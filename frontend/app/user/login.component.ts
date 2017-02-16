import {Component, OnInit} from "@angular/core";
import {UserService} from "./service/user.service";
import {LoginUser} from "./model/login.user";
import {RouterService} from "../core/service/router.service";
import {User} from "./model/user";

@Component({
    templateUrl: '/app/user/views/login.html'
})
export class LoginComponent implements OnInit {

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
        this.userService.userChange$.subscribe(
            (user: User) => {
                if (user && user.id) {
                    this.routerService.goToHome()
                }
            }
        );
    }

    login() {
        this.userService.login(this.loginUser)
    }
}