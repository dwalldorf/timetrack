import {Injectable} from "@angular/core";
import {LoginUser} from "../model/login.user";
import {User} from "../model/user";
import {Observable} from "rxjs/Rx";
import {HttpService} from "../../core/service/http.service";
import {CookieService} from "angular2-cookie/services/cookies.service";

@Injectable()
export class UserService {

    private http: HttpService;

    private cookieService: CookieService;

    private currentUser: User;

    constructor(httpService: HttpService, cookieService: CookieService) {
        this.http = httpService;
        this.cookieService = cookieService;

        this.fetchCurrentUser()
            .subscribe();
    }

    private fetchCurrentUser(): Observable<User> {
        let obs: Observable<User> = this.http.get('http://localhost:8080/users/me');
        obs.subscribe(
            (user: User) => this.currentUser = user,
            () => this.currentUser = undefined);

        return obs;
    }

    login(user: LoginUser): Observable<User> {
        let observable: Observable<User> = this.http.post('http://localhost:8080/users/login', user);
        observable.subscribe(
            () => this.fetchCurrentUser()
        );
        return observable;
    }

    logout(): Observable<any> {
        let observable = this.http.delete('http://localhost:8080/users/logout');
        observable.subscribe(
            () => {
                this.currentUser = undefined;
                this.cookieService.remove('TIMETRACK');
            }
        );

        return observable;
    }

    register(user: User): Observable<any> {
        return this.http.post('http://localhost:8080/users', user);
    }

    isLoggedIn() {
        return this.fetchCurrentUser();

        // let loggedIn: boolean = !!this.currentUser;
        // console.log(loggedIn);
        // if (loggedIn) {
        //     console.log(this.currentUser);
        // }
        // return loggedIn;
    }
}