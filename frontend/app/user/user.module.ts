import {NgModule} from "@angular/core";
import {LoginComponent} from "./login.component";
import {UserService} from "./service/user.service";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {RegisterComponent} from "./register.component";
import {AppRoutingModule} from "../app.routing.module";
import {SettingsComponent} from "./settings.component";

@NgModule({
    declarations: [
        LoginComponent,
        RegisterComponent,
        SettingsComponent
    ],
    imports: [
        AppRoutingModule,
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