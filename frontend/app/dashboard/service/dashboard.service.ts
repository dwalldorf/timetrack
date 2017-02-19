import {Injectable} from "@angular/core";
import {HttpService} from "../../core/service/http.service";
import {Observable} from "rxjs";
import {GraphData} from "../model/graph.data";
import {Response} from "@angular/http";

@Injectable()
export class DashboardService {

    private _httpService: HttpService;

    constructor(httpService: HttpService) {
        this._httpService = httpService;
    }

    public getDefaultStatsData(): Observable<Response> {
        let from = new Date();
        from.setMonth(from.getMonth() - 1);
        let to = new Date();

        return this.getStatsData(from, to);
    }

    private getStatsData(from: Date, to: Date): Observable<Response> {
        let fromStr = this.getDateString(from),
            toStr = this.getDateString(to);

        return this._httpService.get('/worklog/graph_data?from=' + fromStr + '&to=' + toStr);
    }

    private getDateString(date: Date): string {
        return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
    }
}