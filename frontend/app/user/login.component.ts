import {Component} from "@angular/core";
import {UserService} from "./service/user.service";
import {LoginUser} from "./model/login.user";
import {RouterService} from "../core/service/router.service";

@Component({
    templateUrl: '/app/user/views/login.html'
})
export class LoginComponent {

    private routerService: RouterService;

    private userService: UserService;

    private user: LoginUser;

    loginError: string = null;

    constructor(routerService: RouterService, userService: UserService) {
        this.routerService = routerService;
        this.userService = userService;
        this.user = new LoginUser();
    }

    ngOnInit() {
        if (this.userService.isLoggedIn()) {
            console.log('logged in');
            this.routerService.goToHome();
        }
    }

    login() {
        this.userService.login(this.user)
            .subscribe(
                () => this.routerService.goToHome(),
                err => console.log(err)
            );
    }

    private getQueryStringValue(key: string) {
        return decodeURIComponent(window.location.search.replace(new RegExp("^(?:.*[&\\?]" + encodeURIComponent(key).replace(/[\.\+\*]/g, "\\$&") + "(?:\\=([^&]*))?)?.*$", "i"), "$1"));
    }
}