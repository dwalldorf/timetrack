import {Component, AfterViewInit, OnInit} from "@angular/core";
import {UserService} from "../user/service/user.service";
import {DashboardService} from "./service/dashboard.service";
import {Response} from "@angular/http";
import {GraphData} from "./model/graph.data";
import {DateUtil} from "../core/util/date.util";

@Component({
    templateUrl: '/app/dashboard/views/dashboard.html'
})
export class DashboardComponent implements OnInit, AfterViewInit {

    private _dashboardService: DashboardService;

    private _userService: UserService;

    private data: Array<GraphData>;
    private fromDate: string;
    private toDate: string;
    private scale: string;

    private _fromDate: Date;
    private _toDate: Date;

    constructor(dashboardService: DashboardService, userService: UserService) {
        this._dashboardService = dashboardService;
        this._userService = userService;
    }

    ngOnInit(): void {
        this._fromDate = new Date();
        this._fromDate.setMonth(this._fromDate.getMonth() - 1);
        this._toDate = new Date();

        this.fromDate = DateUtil.toHtmlInputDate(this._fromDate);
        this.toDate = DateUtil.toHtmlInputDate(this._toDate);
        this.scale = 'day';
    }

    ngAfterViewInit(): void {
        let user = this._userService.getCurrentUser();
        if (user && user.id) {
            this.getStats()
        }
    }

    changeFromDate(): void {
        this._fromDate = new Date(this.fromDate);
        this.getStats()
    }

    changeToDate(): void {
        this._toDate = new Date(this.toDate);
        this.getStats()
    }

    changeScale(): void {
        this.getStats();
    }

    private getStats(): void {
        this._dashboardService.getStatsData(this._fromDate, this._toDate, this.scale)
            .subscribe((res: Response) => {
                if (res.status === 200) {
                    this.data = res.json();
                }
            });
    }
}