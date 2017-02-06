import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {AppConfig} from "../config/app.config";

@Injectable()
export class RouterService {

    private router: Router;

    constructor(router: Router) {
        this.router = router;
    }

    public goToHome() {
        console.log(AppConfig.ROUTE_HOME);
        this.router.navigateByUrl(AppConfig.ROUTE_HOME);
    }

    public goToLogin() {
        this.router.navigateByUrl(AppConfig.ROUTE_LOGIN);
    }
}