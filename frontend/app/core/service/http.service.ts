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

    private  isValidMethod(method: string) {
        return (HttpService.ALLOWED_METHODS.indexOf(method) >= 0);
    }

    private static getRequestHash(method: string, url: string) {
        return method + ':' + url;
    }

    private _cacheService: CacheService;

    constructor(backend: XHRBackend, options: RequestOptions, cacheService: CacheService) {
        let headers: Headers = options.headers;
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");

        options.headers = headers;
        options.withCredentials = true;

        super(backend, options);

        this._cacheService = cacheService;
    }

    public get(url: string): Observable<any> {
        return this.makeReq(HttpService.METHOD_GET, url, null);
    }

    public post(url: string, body: any): Observable<any> {
        return this.makeReq(HttpService.METHOD_POST, url, body);
    }

    private makeReq(method: string, url: string, body: any): Observable<any> {
        if (!this.isValidMethod(method)) {
            console.error("Invalid method: " + method);
            return;
        }

        let requestHash = HttpService.getRequestHash(method, url);
        let retVal = new EventEmitter<any>();

        let cacheHit = this._cacheService.get(requestHash);
        if (cacheHit) {
            setTimeout(function () {
                retVal.emit(cacheHit);
            }, 10);
            return retVal;
        }

        let observable: Observable<any>;
        switch (method) {
            case HttpService.METHOD_GET:
                observable = super.get(url);
                break;
            case HttpService.METHOD_POST:
                observable = super.post(url, body);
                break;
        }

        observable.map((res: Response) => res.json());
        observable.subscribe(
            (data) => {
                retVal.emit(data);
                this._cacheService.cache(requestHash, data, 1)
            },
            (error) => retVal.error(error)
        );
        return retVal;
    }
}