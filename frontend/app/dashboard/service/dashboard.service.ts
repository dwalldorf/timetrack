import {Injectable} from "@angular/core";
import {HttpService} from "../../core/service/http.service";
import {Observable} from "rxjs";
import {Response} from "@angular/http";

@Injectable()
export class DashboardService {

    private _httpService: HttpService;

    constructor(httpService: HttpService) {
        this._httpService = httpService;
    }

    public getStatsData(from: Date, to: Date, scale: string): Observable<Response> {
        let fromStr = this.getDateString(from),
            toStr = this.getDateString(to);

        return this._httpService.get('/worklog/graph_data?' +
            'from=' + fromStr +
            '&to=' + toStr +
            '&scale=' + scale
        );
    }

    private getDateString(date: Date): string {
        return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
    }
}