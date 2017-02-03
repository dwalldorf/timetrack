import {Component} from "@angular/core";
import {LoginService} from "./login/service/login.service";

@Component({
    selector: 'timetrack-app',
    templateUrl: '/app/views/layout.html'
})
export class AppComponent {

    private loginService: LoginService;

    constructor(loginService: LoginService) {
        this.loginService = loginService;
    }

    getSteamOpenIdLink() {
        this.loginService.getLoginLink();

        let uri = '';
        let str = 'http://steamcommunity.com/openid/login?openid.ns=http:%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.mode=checkid_setup&openid.return_to=http:%2F%2Flocalhost:3000/login&openid.identity=http:%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.claimed_id=http:%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select';

        // this.location.go('http://steamcommunity.com/openid/login');
        return str
    }
}