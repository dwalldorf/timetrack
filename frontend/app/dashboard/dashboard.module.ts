import {NgModule} from "@angular/core";
import {DashboardComponent} from "./dashboard.component";
import {StatsModule} from "../stats/stats.module";
import {DashboardService} from "./service/dashboard.service";
import {FormsModule} from "@angular/forms";

@NgModule({
    imports: [
        FormsModule,
        StatsModule
    ],
    declarations: [
        DashboardComponent
    ],
    providers: [
        DashboardService
    ]
})
export class DashboardModule {
}