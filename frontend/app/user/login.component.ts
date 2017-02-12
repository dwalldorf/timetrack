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

    private loginUser: LoginUser;

    loginError: string = null;

    constructor(routerService: RouterService, userService: UserService) {
        this.routerService = routerService;
        this.userService = userService;
        this.loginUser = new LoginUser();
    }

    ngOnInit() {
        this.userService.fetchCurrentUser()
            .subscribe(
                () => this.routerService.goToHome()
            );
    }

    login() {
        this.userService
            .login(this.loginUser)
            .subscribe(
                () => this.routerService.goToHome()
            );
    }
}