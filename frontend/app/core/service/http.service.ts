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

    constructor(backend: XHRBackend, options: RequestOptions, cacheService: CacheService) {
        let headers: Headers = options.headers;
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");

        options.headers = headers;
        options.withCredentials = true;

        super(backend, options);
    }

    public get(url: string): Observable<any> {
        return this.makeReq(HttpService.METHOD_GET, url, null);
    }

    public post(url: string, body: any): Observable<any> {
        return this.makeReq(HttpService.METHOD_POST, url, body);
    }

    private makeReq(method: string, url: string, body: any): Observable<any> {
        url = 'http://localhost:8080' + url;
        if (!this.isValidMethod(method)) {
            console.error("Invalid method: " + method);
            return;
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
        return observable;
    }
}