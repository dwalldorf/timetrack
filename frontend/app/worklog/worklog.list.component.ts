import {Component, OnInit} from "@angular/core";
import {WorklogService} from "./service/worklog.service";
import {Response} from "@angular/http";
import {WorklogEntryModel} from "./model/worklogentry.model";
import {ListDto} from "../core/model/list.dto";
import {DateUtil} from "../core/util/date.util";
import {RouterService} from "../core/service/router.service";

@Component({
    templateUrl: '/app/worklog/views/list.html'
})
export class WorklogListComponent implements OnInit {

    private _routerService: RouterService;
    private _worklogService: WorklogService;

    private worklogData: ListDto<WorklogEntryModel> = new ListDto<WorklogEntryModel>();

    constructor(routerService: RouterService, worklogService: WorklogService) {
        this._routerService = routerService;
        this._worklogService = worklogService;
    }

    ngOnInit() {
        this.loadData();
    }

    toHours(minutes: number): string {
        let hours = Math.floor(minutes / 60),
            minutesOfHour = minutes % 60;

        return hours + ':' + minutesOfHour;

        // return DateUtil.toHours(minutes);
    }

    timeFormat(date: any): string {
        if (date) {
            return DateUtil.timeFormat(date);
        }
        return '';
    }

    dateTimeFormat(date: any): string {
        if (date) {
            return DateUtil.dateTimeFormatJodaDateTime(date);
        }
        return '';
    }

    onSelect(entry: WorklogEntryModel): void {
        this._routerService.goToEditWorklogEntry(entry.id);
    }

    onDelete(entry: WorklogEntryModel): void {
        this._worklogService.delete(entry)
            .subscribe(() => this.loadData());
    }

    private loadData(): void {
        this._worklogService.getWorklog()
            .subscribe((res: Response) => {
                if (res.status === 200) {
                    this.worklogData = res.json();
                }
            });
    }
}