import {Component} from "@angular/core";
import {UserService} from "./service/user.service";
import {User} from "./model/user";
import {Router} from "@angular/router";

@Component({
    templateUrl: '/app/user/views/login.html'
})
export class LoginComponent {

    private router: Router;

    private userService: UserService;

    private user: User;

    loginError: string = null;

    constructor(router: Router, userService: UserService) {
        this.router = router;
        this.userService = userService;
        this.user = new User();
    }

    ngOnInit() {
        this.userService
            .getCurrentUser()
            .subscribe(() => {
                    this.router.navigateByUrl('/');
                }, () => {
                    console.log('not logged in');
                }
            );
    }

    login() {
        this.userService.login(this.user)
            .subscribe(
                () => this.router.navigateByUrl('/'),
                err => console.log(err)
            );
    }

    private getQueryStringValue(key: string) {
        return decodeURIComponent(window.location.search.replace(new RegExp("^(?:.*[&\\?]" + encodeURIComponent(key).replace(/[\.\+\*]/g, "\\$&") + "(?:\\=([^&]*))?)?.*$", "i"), "$1"));
    }
}