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

    constructor(userService: UserService, routerService: RouterService) {
        this.routerService = routerService;
        this.userService = userService;
    }

    ngOnInit() {
        if (!this.userService.isLoggedIn()) {
            this.routerService.goToLogin();
        }
    }

    logout() {
        this.userService.logout();
    }
}