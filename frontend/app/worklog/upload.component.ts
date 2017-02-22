import {Component} from "@angular/core";
import {FileUploader} from "ng2-file-upload";

@Component({
    selector: 'upload-csv',
    templateUrl: '/app/worklog/views/upload.html'
})
export class UploadComponent {

    private uploader: FileUploader = new FileUploader({url: 'http://localhost:8080/csv'});
}