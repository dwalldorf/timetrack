import {NgModule} from "@angular/core";
import {StatsComponent} from "./stats.component";
import {ChartsModule} from 'ng2-charts'
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";

@NgModule({
    declarations: [
        StatsComponent
    ],
    imports:[
        ChartsModule,
        CommonModule
    ],
    exports: [
        StatsComponent
    ]
})
export class StatsModule {
}