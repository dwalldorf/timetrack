import {Injectable} from "@angular/core";
import {Http, XHRBackend, RequestOptions, Response, RequestOptionsArgs, Request} from "@angular/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class HttpService extends Http {

    constructor(backend: XHRBackend, options: RequestOptions) {
        super(backend, options);
    }

    request(url: string|Request, options?: RequestOptionsArgs): Observable<Response> {
        return super.request(url, options);
    }

    get(url: string) {
        return super.get(url);
    }
}