import {NgModule} from "@angular/core";
import {HttpService} from "./service/http.service";
import {RouterService} from "./service/router.service";

@NgModule({
    providers: [
        HttpService,
        RouterService
    ]
})
export class CoreModule {
}