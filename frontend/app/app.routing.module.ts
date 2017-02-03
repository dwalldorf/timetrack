import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {BindingComponent} from "./bindings/binding.component";
import {LoginComponent} from "./login/login.component";

const appRoutes: Routes = [
    {path: '', component: BindingComponent},
    {path: 'login', component: LoginComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}