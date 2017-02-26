import {NgModule} from "@angular/core";
import {WorklogService} from "./service/worklog.service";
import {CommonModule} from "@angular/common";
import {FileUploadModule} from "ng2-file-upload";
import {WorklogEditComponent} from "./worklog.edit.component";
import {WorklogButtonBarComponent} from "./worklog.buttonbar.component";

@NgModule({
    declarations: [
        WorklogButtonBarComponent,
        WorklogEditComponent
    ],
    imports: [
        CommonModule,g
        FileUploadModule
    ],
    providers: [
        WorklogService
    ],
    exports: [
        WorklogButtonBarComponent,
        WorklogEditComponent
    ]
})
export class WorklogModule {
}