import {NgModule} from "@angular/core";
import {LoginComponent} from "./login.component";
import {UserService} from "./service/user.service";
import {Http, HttpModule} from "@angular/http";

@NgModule({
    declarations: [
        LoginComponent
    ],
    providers: [
        Http,
        HttpModule,
        UserService,
    ]
})
export class LoginModule {
}