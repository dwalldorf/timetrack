import {Component} from "@angular/core";
import {WorklogService} from "./service/worklog.service";

@Component({
    templateUrl: '/app/worklog/views/edit.html'
})
export class WorklogEditComponent {

    private _worklogService: WorklogService;

    constructor(worklogService: WorklogService) {
        this._worklogService = worklogService;
    }
}