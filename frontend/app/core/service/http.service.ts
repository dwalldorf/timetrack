import {Http, XHRBackend, RequestOptions, Headers, Response} from "@angular/http";
import {Injectable, EventEmitter} from "@angular/core";
import {Observable} from "rxjs/Rx";
import {CacheService} from "./cache.service";

@Injectable()
export class HttpService extends Http {

    private static METHOD_GET = 'get';
    private static METHOD_POST = 'post';
    private static METHOD_DELETE = 'delete';
    private static METHOD_PUT = 'put';

    private static ALLOWED_METHODS = [
        HttpService.METHOD_GET,
        HttpService.METHOD_POST,
        HttpService.METHOD_DELETE,
        HttpService.METHOD_PUT
    ];

    private static isValidMethod(method: string) {
        return (HttpService.ALLOWED_METHODS.indexOf(method) >= 0);
    }

    private static getRequestHash(method: string, url: string) {
        return method + ':' + url;
    }

    private cacheService: CacheService;

    private requestsInProgress: {[key: string]: Observable<any>};

    constructor(backend: XHRBackend, options: RequestOptions, cacheService: CacheService) {
        let headers: Headers = options.headers;
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");

        options.headers = headers;
        options.withCredentials = true;

        super(backend, options);

        this.cacheService = cacheService;
        this.requestsInProgress = {};
    }

    get(url: string): Observable<any> {
        return this.makeReq(HttpService.METHOD_GET, url, null);
    }

    post(url: string, body: any): Observable<any> {
        return this.makeReq(HttpService.METHOD_POST, url, body);
    }

    private makeReq(method: string, url: string, body: any): Observable<any> {
        let requestHash = HttpService.getRequestHash(method, url);

        if (this.hasRequestInProgress(requestHash)) {
            return this.getRequestInProgress(requestHash);
        }

        let cacheHit = this.cacheService.get(requestHash);
        if (cacheHit != null) {
            console.log(requestHash + ' found cache');
            // return new Observable().from(cacheHit);
            return cacheHit;
        }

        let observable: Observable<any>;
        switch (method) {
            case HttpService.METHOD_GET:
                observable = super.get(url);
                break;
            case HttpService.METHOD_POST:
                observable = super.post(url, body);
        }
        this.requestsInProgress[requestHash] = observable;

        observable.map((res: Response) => res.json());
        observable.subscribe(
            (data) => {
                this.finishRequest(requestHash);
                this.cacheService.cache(requestHash, data, 1)
            },
            (error) => this.finishRequest(requestHash),
            () => this.finishRequest(requestHash)
        );
        return observable;
    }

    // makeRequest(method: string, url: string, payload: any = null): EventEmitter<any> {
    //     if (!HttpService.isValidMethod(method)) {
    //         throw new Error('invalid http method: ' + method);
    //     }
    //
    //     // url = AppConfig.API_PREFIX + url;
    //     let requestEventEmitter = new EventEmitter<any>(),
    //         requestHash = HttpService.getRequestHash(method, url);
    //
    //     if (this.hasRequestInProgress(requestHash)) {
    //         return this.getRequestInProgress(requestHash);
    //     }
    //
    //     // payload = JSON.stringify(payload);
    //     let observable: Observable<Response>;
    //
    //     switch (method) {
    //         case HttpService.METHOD_GET:
    //             observable = super.get(url);
    //             break;
    //         case HttpService.METHOD_POST:
    //             observable = super.post(url, payload);
    //             break;
    //         case HttpService.METHOD_PUT:
    //             observable = super.put(url, payload);
    //             break;
    //         case HttpService.METHOD_DELETE:
    //             observable = super.delete(url);
    //             break;
    //     }
    //
    //     this.requestsInProgress[requestHash] = requestEventEmitter;
    //
    //     observable.subscribe(
    //         res => {
    //             let emitVal = true;
    //             try {
    //                 emitVal = res.json();
    //             } catch (error) {
    //             }
    //
    //             requestEventEmitter.emit(emitVal);
    //             this.finishRequest(requestHash);
    //         },
    //         err => {
    //             requestEventEmitter.error(err);
    //             this.finishRequest(requestHash);
    //         },
    //         () => {
    //             this.finishRequest(requestHash);
    //         }
    //     );
    //     return requestEventEmitter;
    // }

    private finishRequest(requestHash: string) {
        delete this.requestsInProgress[requestHash];
    }

    private hasRequestInProgress(requestHash: string) {
        return this.requestsInProgress.hasOwnProperty(requestHash);
    }

    /**
     * @param requestHash string
     * @returns EventEmitter
     */
    private getRequestInProgress(requestHash: string) {
        if (!this.hasRequestInProgress(requestHash)) {
            throw new Error('no such request in progress: ' + requestHash);
        }

        return this.requestsInProgress[requestHash];
    }
}