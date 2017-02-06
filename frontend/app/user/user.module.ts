import {NgModule} from "@angular/core";
import {LoginComponent} from "./login.component";
import {UserService} from "./service/user.service";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {RegisterComponent} from "./register.component";

@NgModule({
    declarations: [
        LoginComponent,
        RegisterComponent
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