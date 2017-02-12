import {Component, Output} from "@angular/core";
import {UserService} from "./user/service/user.service";
import {RouterService} from "./core/service/router.service";
import {AppConfig} from "./core/config/app.config";
import {User} from "./user/model/user";

@Component({
    selector: 'timetrack-app',
    templateUrl: '/app/views/layout.html',
})
export class AppComponent {

    private userService: UserService;

    private routerService: RouterService;

    isLoggedIn: boolean = false;

    user: User = null;

    constructor(userService: UserService, routerService: RouterService) {
        this.routerService = routerService;
        this.userService = userService;
    }

    ngOnInit() {
        this.userService.fetchCurrentUser()
            .subscribe(
                (user: User) => this.handleLoggedIn(user),
                () => this.handleNotLoggedIn()
            );
    }

    logout() {
        this.userService.logout()
            .subscribe(
                () => this.routerService.goToLogin()
            );
    }

    private handleLoggedIn(user: User) {
        console.log('handleLoggedIn');

        this.user = user;
        this.isLoggedIn = true;
    }

    private handleNotLoggedIn() {
        console.log('handleNotLoggedIn');

        this.isLoggedIn = false;
        this.user = null;

        let currentRoute = this.routerService.getCurrentRoute();
        if (currentRoute !== AppConfig.ROUTE_LOGIN && currentRoute !== AppConfig.ROUTE_REGISTER) {
            this.routerService.goToLogin();
        }
    }
}