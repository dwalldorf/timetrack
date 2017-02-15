import {NgModule} from "@angular/core";
import {StatsComponent} from "./stats.component";

@NgModule({
    declarations: [
        StatsComponent
    ],
    exports: [
        StatsComponent
    ]
})
export class StatsModule {
}