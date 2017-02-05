import {Injectable} from "@angular/core";
import {Http} from "@angular/http";

@Injectable()
export class UserService {

    private http: Http;

    constructor(http:Http){
        this.http = http;
    }

    public login(claimedId: string) {

    }

    public getLoginLink(){
        // this.http.get("http://localhost:8080/login/steam_openid_url");
    }
}