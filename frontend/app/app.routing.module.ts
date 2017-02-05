import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {LoginComponent} from "./user/login.component";
import {DashboardComponent} from "./dashboard/dashboard.component";

const appRoutes: Routes = [
    {path: '', component: DashboardComponent},
    {path: 'login', component: LoginComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}