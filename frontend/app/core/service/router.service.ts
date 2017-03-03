import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {AppConfig} from "../config/app.config";
import {WorklogEntryModel} from "../../worklog/model/worklogentry.model";

@Injectable()
export class RouterService {

    private router: Router;

    constructor(router: Router) {
        this.router = router;
    }

    public goToHome(): void {
        this.router.navigateByUrl(AppConfig.ROUTE_HOME);
    }

    public goToLogin(): void {
        this.router.navigateByUrl(AppConfig.ROUTE_LOGIN);
    }

    public goToEditWorklogEntry(id: string): void {
        this.router.navigate(['/worklog-edit', id]);
    }

    public goToWorklogList(): void {
        this.router.navigateByUrl(AppConfig.ROUTE_WORKLOG);
    }

    public getCurrentRoute(): string {
        return this.router.url;
    }
}