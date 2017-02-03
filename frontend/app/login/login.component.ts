import {Component} from "@angular/core";
import {isNullOrUndefined} from "util";
import {LoginService} from "./service/login.service";

@Component({
    template: ''
})
export class LoginComponent {

    private loginService: LoginService;

    constructor(loginService: LoginService) {
        this.loginService = loginService;
    }

    ngOnInit() {
        let claimedId = this.getQueryStringValue('openid.claimed_id');
        if (!isNullOrUndefined(claimedId)) {
            this.loginService.login(claimedId);
        }
    }


    private getQueryStringValue(key: string) {

        return decodeURIComponent(window.location.search.replace(new RegExp("^(?:.*[&\\?]" + encodeURIComponent(key).replace(/[\.\+\*]/g, "\\$&") + "(?:\\=([^&]*))?)?.*$", "i"), "$1"));
    }
}