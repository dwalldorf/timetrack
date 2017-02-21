import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {LoginComponent} from "./user/login.component";
import {RegisterComponent} from "./user/register.component";
import {AppConfig} from "./core/config/app.config";

const appRoutes: Routes = [
    {
        path: AppConfig.getRouterLink(AppConfig.ROUTE_HOME),
        component: DashboardComponent,
        data: {requireLogin: true}
    },
    {
        path: AppConfig.getRouterLink(AppConfig.ROUTE_LOGIN),
        component: LoginComponent,
        data: {requireLogin: false}
    },
    {
        path: AppConfig.getRouterLink(AppConfig.ROUTE_REGISTER),
        component: RegisterComponent,
        data: {requireLogin: false}
    },
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}