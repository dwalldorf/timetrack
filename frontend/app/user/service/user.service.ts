import {Injectable, EventEmitter} from "@angular/core";
import {LoginUser} from "../model/login.user";
import {User} from "../model/user";
import {Observable, BehaviorSubject} from "rxjs/Rx";
import {HttpService} from "../../core/service/http.service";
import {CookieService} from "angular2-cookie/services/cookies.service";
import {Response} from "@angular/http";

@Injectable()
export class UserService {

    private readonly KEY_CURRENT_USER = 'cu';

    private httpService: HttpService;

    private cookieService: CookieService;

    private userChange = new BehaviorSubject<User>(null);
    public userChange$ = this.userChange.asObservable();

    constructor(httpService: HttpService, cookieService: CookieService) {
        this.httpService = httpService;
        this.cookieService = cookieService;

        this.fetchCurrentUser();
    }

    public fetchCurrentUser(): EventEmitter<User> {
        let emitter = new EventEmitter<User>();

        this.httpService
            .get('http://localhost:8080/users/me')
            .subscribe(
                (res: Response) => {
                    if (res.status !== 200) {
                        this.handleLogout()
                    } else {
                        let user = res.json();
                        localStorage.setItem(this.KEY_CURRENT_USER, JSON.stringify(user));

                        emitter.next(user);
                        this.userChange.next(user);
                    }
                },
                () => this.handleLogout()
            );
        return emitter;
    }

    public login(user: LoginUser): Observable<any> {
        let observable: Observable<any> = this.httpService.post('http://localhost:8080/users/login', user);
        observable.subscribe(
            (user: User) => {
                this.fetchCurrentUser();
            }
        );
        return observable;
    }

    public getCurrentUser(): User {
        let userStr = localStorage.getItem(this.KEY_CURRENT_USER);
        if (!userStr) {
            return null;
        }

        return JSON.parse(userStr);
    }

    public logout(): Observable<Response> {
        let observable: Observable<Response> = this.httpService.post('http://localhost:8080/users/logout', null);
        observable.subscribe(
            () => {
                this.handleLogout();
            }
        );

        return observable;
    }

    public register(user: User): Observable<any> {
        return this.httpService.post('http://localhost:8080/users', user);
    }

    private handleLogout(){
        this.cookieService.remove('TIMETRACK');
        localStorage.removeItem(this.KEY_CURRENT_USER);
        this.userChange.next(null);
    }
}