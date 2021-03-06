import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {XHRBackend, RequestOptions} from "@angular/http";
import {CommonModule} from "@angular/common";
import {AppComponent} from "./app.component";
import {AppRoutingModule} from "./app.routing.module";
import {HttpService} from "./core/service/http.service";
import {UserModule} from "./user/user.module";
import {DashboardModule} from "./dashboard/dashboard.module";
import {CoreModule} from "./core/core.module";
import {CookieService} from "angular2-cookie/services/cookies.service";
import {StatsModule} from "./stats/stats.module";
import {WorklogModule} from "./worklog/worklog.module";

@NgModule({
    imports: [
        BrowserModule,
        CommonModule,
        FormsModule,

        AppRoutingModule,

        CoreModule,
        StatsModule,
        DashboardModule,
        UserModule,
        WorklogModule
    ],
    providers: [
        {
            provide: [HttpService],
            useFactory: (backend: XHRBackend, options: RequestOptions) => {
                return new HttpService(backend, options);
            },
            deps: [XHRBackend, RequestOptions]
        },
        CookieService
    ],
    declarations: [
        AppComponent
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}