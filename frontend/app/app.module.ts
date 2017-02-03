import {NgModule} from "@angular/core";
import {AppComponent} from "./app.component";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {AppRoutingModule} from "./app.routing.module";
import {BindingsModule} from "./bindings/bindings.module";
import {LoginModule} from "./login/login.module";
import {HttpModule, Http} from "@angular/http";

@NgModule({
    imports: [
        AppRoutingModule,
        BrowserModule,
        FormsModule,
        HttpModule,

        BindingsModule,
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