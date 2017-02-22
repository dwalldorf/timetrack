import {NgModule} from "@angular/core";
import {UploadComponent} from "./upload.component";
import {WorklogService} from "./service/worklog.service";
import {CommonModule} from "@angular/common";
import {FileUploadModule} from "ng2-file-upload";

@NgModule({
    declarations: [
        UploadComponent
    ],
    imports: [
        CommonModule,
        FileUploadModule
    ],
    providers: [
        WorklogService
    ],
    exports: [
        UploadComponent
    ]
})
export class WorklogModule {
}