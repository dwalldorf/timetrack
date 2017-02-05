import {NgModule} from "@angular/core";
import {AppComponent} from "./app.component";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {AppRoutingModule} from "./app.routing.module";
import {UserModule} from "./user/user.module";
import {XHRBackend, RequestOptions} from "@angular/http";
import {CommonModule} from "@angular/common";
import {HttpService} from "./core/service/http.service";
import {DashboardModule} from "./dashboard/dashboard.module";

@NgModule({
    imports: [
        AppRoutingModule,
        BrowserModule,
        CommonModule,
        FormsModule,

        DashboardModule,
        UserModule
    ],
    providers: [
        {
            provide: HttpService,
            useFactory: (backend: XHRBackend, options: RequestOptions) => {
                return new HttpService(backend, options);
            },
            deps: [XHRBackend, RequestOptions]
        }
    ],
    declarations: [
        AppComponent
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}