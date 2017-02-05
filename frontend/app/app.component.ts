import {Component} from "@angular/core";
import {UserService} from "./user/service/user.service";

@Component({
    selector: 'timetrack-app',
    templateUrl: '/app/views/layout.html'
})
export class AppComponent {

    private userService: UserService;

    constructor(userService: UserService) {
        this.userService = userService;
    }

    logout() {
        this.userService.logout();
    }
}