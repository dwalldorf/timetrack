import {Component} from "@angular/core";
import {UserService} from "./user/service/user.service";
import {RouterService} from "./core/service/router.service";

@Component({
    selector: 'timetrack-app',
    templateUrl: '/app/views/layout.html'
})
export class AppComponent {

    private userService: UserService;

    private routerService: RouterService;

    isLoggedIn: boolean = true;

    constructor(userService: UserService, routerService: RouterService) {
        this.routerService = routerService;
        this.userService = userService;
    }

    ngOnInit() {
        this.userService.userEventEmitter
            .subscribe(
                () => this.isLoggedIn = true,
                () => this.handleNotLoggedIn()
            );
    }

    logout() {
        this.userService.logout()
            .subscribe(
                () => this.routerService.goToLogin()
            );
    }

    private handleNotLoggedIn() {
        console.log('not logged in');
        this.isLoggedIn = false;
        this.routerService.goToLogin();
    }
}