import {Injectable} from "@angular/core";
import {HttpService} from "../../core/service/http.service";

@Injectable()
export class WorklogService {

    private _httpService: HttpService;

    constructor(httpService: HttpService) {
        this._httpService = httpService;
    }

    public uploadCsv(file: File) {
        this._httpService.postFile('/csv', file);
    }
}