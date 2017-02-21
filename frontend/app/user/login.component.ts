import {Component, OnInit, ViewChild, ElementRef} from "@angular/core";
import {UserService} from "./service/user.service";
import {LoginUser} from "./model/login.user";
import {RouterService} from "../core/service/router.service";
import {User} from "./model/user";

@Component({
    templateUrl: '/app/user/views/login.html'
})
export class LoginComponent implements OnInit {

    @ViewChild('usernameInput')
    private _usernameInput: ElementRef;

    private _routerService: RouterService;

    private _userService: UserService;

    private loginUser: LoginUser;

    loginError: string = null;

    constructor(routerService: RouterService, userService: UserService) {
        this._routerService = routerService;
        this._userService = userService;
        this.loginUser = new LoginUser();
    }

    ngOnInit() {
        this._usernameInput.nativeElement.focus();

        this._userService.userChange$.subscribe(
            (user: User) => {
                if (user && user.id) {
                    this._routerService.goToHome()
                }
            }
        );
    }

    login() {
        this._userService.login(this.loginUser)
    }
}