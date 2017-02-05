import {NgModule} from "@angular/core";
import {AppComponent} from "./app.component";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {AppRoutingModule} from "./app.routing.module";
import {LoginModule} from "./user/login.module";
import {HttpModule, Http} from "@angular/http";

@NgModule({
    imports: [
        AppRoutingModule,
        BrowserModule,
        FormsModule,
        HttpModule,

        LoginModule
    ],
    declarations: [
        AppComponent
    ],
    providers: [Http, HttpModule],
    bootstrap: [AppComponent]
})
export class AppModule {
}