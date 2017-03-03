import {Component, OnInit} from "@angular/core";
import {WorklogService} from "./service/worklog.service";
import {WorklogEntryModel} from "./model/worklogentry.model";
import {RouterService} from "../core/service/router.service";
import {ActivatedRoute} from "@angular/router";
import {Response} from "@angular/http";
import {DateUtil} from "../core/util/date.util";
import {CompleterService, CompleterData} from "ng2-completer";
import {AppConfig} from "../core/config/app.config";

@Component({
    selector: 'worklog-edit',
    templateUrl: '/app/worklog/views/edit.html'
})
export class WorklogEditComponent implements OnInit {

    private title_add = 'Add Worklog entry';
    private title_edit = 'Edit Worklog entry';

    private _route: ActivatedRoute;
    private _routerService: RouterService;
    private _worklogService: WorklogService;

    private title: string = this.title_add;

    private entry: WorklogEntryModel = new WorklogEntryModel;

    private customerCompletionDataService: CompleterData;
    private projectCompletionDataService: CompleterData;

    constructor(route: ActivatedRoute, routerService: RouterService, worklogService: WorklogService, completerService: CompleterService) {
        this._route = route;
        this._routerService = routerService;
        this._worklogService = worklogService;

        this.customerCompletionDataService = completerService.remote(AppConfig.BACKEND_URI_WORKLOG_SEARCH_CUSTOMERS);
        this.projectCompletionDataService = completerService.remote(AppConfig.BACKEND_URI_WORKLOG_SEARCH_PROJECTS);
    }

    ngOnInit() {
        this._route.params.subscribe(params => {
            let id = params['id'];

            if (id) {
                this.title = this.title_edit;

                this._worklogService.getById(id)
                    .subscribe((res: Response) => {
                        if (res.status === 200) {
                            this.entry = res.json();

                            // format dates for html input
                            if (this.entry.start) {
                                this.entry.start = DateUtil.dateTimeHtmlInputFormat(this.entry.start);
                            }
                            if (this.entry.stop) {
                                this.entry.stop = DateUtil.dateTimeHtmlInputFormat(this.entry.stop);
                            }
                        }
                    });
            }
        });
    }

    saveWorklogEntry(): void {
        this._worklogService.save(this.entry)
            .subscribe((res: Response) => {
                if (res.status === 200 || res.status === 201) {
                    this._routerService.goToWorklogList();
                }
            });
    }

    cancel(): void {
        this.entry = new WorklogEntryModel();

        if (this.entry.id) {
            this._routerService.goToWorklogList();
        }
    }
}