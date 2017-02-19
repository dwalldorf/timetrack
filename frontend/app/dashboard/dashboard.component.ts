import {Component, AfterViewInit} from "@angular/core";
import {UserService} from "../user/service/user.service";
import {DashboardService} from "./service/dashboard.service";
import {Response} from "@angular/http";
import {GraphData} from "./model/graph.data";

@Component({
    templateUrl: '/app/dashboard/views/dashboard.html'
})
export class DashboardComponent implements AfterViewInit {

    private _dashboardService: DashboardService;

    private _userService: UserService;

    constructor(dashboardService: DashboardService, userService: UserService) {
        this._dashboardService = dashboardService;
        this._userService = userService;
    }

    ngAfterViewInit() {
        let user = this._userService.getCurrentUser();
        if (user && user.id) {
            this._dashboardService.getDefaultStatsData()
                .subscribe(
                    (res: Response) => this.render(res.json())
                );
        }
    }

    private render(data: Array<GraphData>): void {
        console.log(data);
    }
}