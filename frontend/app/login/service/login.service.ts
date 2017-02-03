import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import * as http from "selenium-webdriver/http";

@Injectable()
export class LoginService {

    private http: Http;

    constructor(http:Http){
        this.http = http;
    }

    public login(claimedId: string) {

    }

    public getLoginLink(){
        this.http.get("http://localhost:8080/login/steam_openid_url");
    }
}