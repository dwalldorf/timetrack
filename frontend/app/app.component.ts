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

    isLoggedIn: boolean = false;

    currentUser: User = null;

    constructor(userService: UserService, routerService: RouterService) {
        this._routerService = routerService;
        this._userService = userService;
    }

    ngOnInit() {
        // register to userChange so we can redirect to login
        this._userService.userChange$.subscribe(
            (user: User) => {
                if (user) {
                    this.handleLoggedIn(user)
                } else {
                    this.handleNotLoggedIn();
                }
            },
            () => this.handleNotLoggedIn()
        );
    }

    logout() {
        this._userService.logout();
    }

    private handleLoggedIn(user: User) {
        console.log(this._userService.getCurrentUser());
        console.log(user);

        this.isLoggedIn = true;
        this.currentUser = user;
    }

    private handleNotLoggedIn() {
        console.log('handleNotLoggedIn');

        this.isLoggedIn = false;
        this.currentUser = null;

        let currentRoute = this._routerService.getCurrentRoute();
        console.log(currentRoute);
        if (currentRoute !== AppConfig.ROUTE_LOGIN && currentRoute !== AppConfig.ROUTE_REGISTER) {
            console.log('redirecting to login');
            this._routerService.goToLogin();
        }
    }
}