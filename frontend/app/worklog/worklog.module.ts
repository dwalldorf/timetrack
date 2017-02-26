import {NgModule} from "@angular/core";
import {WorklogService} from "./service/worklog.service";
import {CommonModule} from "@angular/common";
import {FileUploadModule} from "ng2-file-upload";
import {WorklogEditComponent} from "./worklog.edit.component";
import {WorklogAddComponent} from "./worklog.add.component";

@NgModule({
    declarations: [
        WorklogAddComponent,
        WorklogEditComponent
    ],
    imports: [
        CommonModule,
        FileUploadModule
    ],
    providers: [
        WorklogService
    ],
    exports: [
        WorklogAddComponent,
        WorklogEditComponent
    ]
})
export class WorklogModule {
}