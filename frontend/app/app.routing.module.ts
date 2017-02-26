import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {LoginComponent} from "./user/login.component";
import {RegisterComponent} from "./user/register.component";
import {AppConfig} from "./core/config/app.config";
import {SettingsComponent} from "./user/settings.component";
import {WorklogEditComponent} from "./worklog/worklog.edit.component";

const appRoutes: Routes = [
    {
        path: AppConfig.getRouterLink(AppConfig.ROUTE_HOME),
        component: DashboardComponent
    },
    {
        path: AppConfig.getRouterLink(AppConfig.ROUTE_LOGIN),
        component: LoginComponent
    },
    {
        path: AppConfig.getRouterLink(AppConfig.ROUTE_REGISTER),
        component: RegisterComponent
    },
    {
        path: AppConfig.getRouterLink(AppConfig.ROUTE_SETTINGS),
        component: SettingsComponent
    },
    {
        path: AppConfig.getRouterLink(AppConfig.ROUTE_WORKLOG_ADD),
        component: WorklogEditComponent
    }
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}