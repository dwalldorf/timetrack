import {Component} from "@angular/core";
import {FileUploader} from "ng2-file-upload";

@Component({
    selector: 'worklog-add',
    templateUrl: '/app/worklog/views/add.html'
})
export class WorklogButtonBarComponent {

    private uploader: FileUploader = new FileUploader({url: 'http://localhost:8080/csv'});

}