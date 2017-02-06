import {NgModule} from "@angular/core";
import {HttpService} from "./service/http.service";
import {RouterService} from "./service/router.service";
import {EmitterService} from "./service/emitter.service";

@NgModule({
    providers: [
        EmitterService,
        HttpService,
        RouterService
    ]
})
export class CoreModule {
}