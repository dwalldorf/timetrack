import {Injectable} from "@angular/core";
import {HttpService} from "../../core/service/http.service";
import {WorklogEntryModel} from "../model/worklogentry.model";
import {Observable} from "rxjs";
import {Response} from "@angular/http";
import {DateUtil} from "../../core/util/date.util";

@Injectable()
export class WorklogService {

    private _httpService: HttpService;

    constructor(httpService: HttpService) {
        this._httpService = httpService;
    }

    getWorklog(): Observable<any> {
        return this._httpService.get('/worklogs')
    }

    getById(id: string): Observable<Response> {
        return this._httpService.get('/worklogs/' + id);
    }

    save(entry: WorklogEntryModel): Observable<Response> {
        // if (entry.start) {
        //     entry.start = DateUtil.stringToJodaParseableDateTime(entry.start);
        // }
        // if (entry.stop) {
        //     entry.stop = DateUtil.stringToJodaParseableDateTime(entry.stop);
        // }

        let observable: Observable<any>;
        if (entry.id) {
            observable = this._httpService.put('/worklogs/' + entry.id, entry);
        } else {
            observable = this._httpService.post('/worklogs', entry);
        }
        return observable
    }

    delete(entry:WorklogEntryModel):Observable<Response>{
        if (!entry||!entry.id) {
            return;
        }

        return this._httpService.delete('/worklogs/' + entry.id);
    }
}