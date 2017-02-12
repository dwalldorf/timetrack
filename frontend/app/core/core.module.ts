import {NgModule} from "@angular/core";
import {HttpService} from "./service/http.service";
import {RouterService} from "./service/router.service";
import {CacheService} from "./service/cache.service";

@NgModule({
    providers: [
        CacheService,
        HttpService,
        RouterService
    ]
})
export class CoreModule {
}