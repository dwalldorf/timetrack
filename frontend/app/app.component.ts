import {Component, OnInit} from "@angular/core";
import {UserService} from "./user/service/user.service";
import {RouterService} from "./core/service/router.service";
import {AppConfig} from "./core/config/app.config";
import {User} from "./user/model/user";

@Component({
    selector: 'timetrack-app',
    templateUrl: '/app/views/layout.html',
})
export class AppComponent implements OnInit {

    private _userService: UserService;

    private _routerService: RouterService;

    private currentUser: User = null;

    constructor(userService: UserService, routerService: RouterService) {
        this._routerService = routerService;
        this._userService = userService;
    }

    ngOnInit() {
        // register to userChange so we can redirect to login
        this._userService.userChange$.subscribe(
            (user: User) => {
                if (user) {
                    this.handleUserChange(user);
                }
            }
        );
    }

    logout() {
        this._userService.logout();
    }

    private handleUserChange(user: User): void {
        if (user) {
            if (user.id) {
                this.handleLoggedIn(user);
            } else {
                this.handleNotLoggedIn();
            }
        }
    }

    private handleLoggedIn(user: User) {
        this.currentUser = user;
    }

    private handleNotLoggedIn() {
        this.currentUser = null;

        let currentRoute = this._routerService.getCurrentRoute();
        if (currentRoute !== AppConfig.ROUTE_LOGIN && currentRoute !== AppConfig.ROUTE_REGISTER) {
            console.log('redirecting to login');
            this._routerService.goToLogin();
        }
    }
}