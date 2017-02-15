import {NgModule} from "@angular/core";
import {DashboardComponent} from "./dashboard.component";
import {StatsModule} from "../stats/stats.module";

@NgModule({
    imports: [
        StatsModule
    ],
    declarations: [
        DashboardComponent
    ]
})
export class DashboardModule {
}