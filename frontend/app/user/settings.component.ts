import {Component, OnInit} from "@angular/core";
import {UserService} from "./service/user.service";
import {User} from "./model/user";
@Component({
    templateUrl: '/app/user/views/settings.html'
})
export class SettingsComponent implements OnInit {

    private _userService: UserService;

    private user: User;

    constructor(userService: UserService) {
        this._userService = userService;
    }

    ngOnInit() {
        this.user = this._userService.getCurrentUser();
    }

    save(): void {
        this._userService.update(this.user);
    }
}