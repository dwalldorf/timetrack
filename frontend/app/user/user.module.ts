import {NgModule} from "@angular/core";
import {LoginComponent} from "./login.component";
import {UserService} from "./service/user.service";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

@NgModule({
    declarations: [
        LoginComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        HttpModule
    ],
    providers: [
        UserService,
    ]
})
export class UserModule {
}