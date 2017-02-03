import {NgModule} from "@angular/core";
import {LoginComponent} from "./login.component";
import {LoginService} from "./service/login.service";
import {Http, HttpModule} from "@angular/http";

@NgModule({
    declarations: [
        LoginComponent
    ],
    providers: [
        Http,
        HttpModule,
        LoginService,
    ]
})
export class LoginModule {
}