import {Component} from "@angular/core";
import {UserService} from "./service/user.service";

@Component({
    template: ''
})
export class LoginComponent {

    private userService: UserService;

    constructor(loginService: UserService) {
        this.userService = loginService;
    }

    ngOnInit() {
        let claimedId = this.getQueryStringValue('openid.claimed_id');
        if (claimedId === undefined) {
            this.userService.login(claimedId);
        }
    }


    private getQueryStringValue(key: string) {

        return decodeURIComponent(window.location.search.replace(new RegExp("^(?:.*[&\\?]" + encodeURIComponent(key).replace(/[\.\+\*]/g, "\\$&") + "(?:\\=([^&]*))?)?.*$", "i"), "$1"));
    }
}