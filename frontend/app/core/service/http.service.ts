import {Http, XHRBackend, RequestOptions, Response, Headers} from "@angular/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Rx";

@Injectable()
export class HttpService extends Http {

    constructor(backend: XHRBackend, options: RequestOptions) {
        let headers: Headers = options.headers;
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");

        options.headers = headers;
        options.withCredentials = true;

        super(backend, options);
    }

    get(url: string) {
        return super.get(url)
            .map((res: Response) => res.json())
            .catch((error: any) => Observable.throw(error));
    }

    post(url: string, body: any) {
        return super.post(url, body)
            .map((res: Response) => res.json())
            .catch((error: any) => Observable.throw(error));
    }

    put(url: string, body: any) {
        return super.put(url, body)
            .map((res: Response) => res.json())
            .catch((error: any) => Observable.throw(error));
    }

    delete(url: string) {
        return super.delete(url)
            .map((res: Response) => res.json())
            .catch((error: any) => Observable.throw(error));
    }
}