import {Injectable} from "@angular/core";
import {LoginUser} from "../model/login.user";
import {User} from "../model/user";
import {Observable, BehaviorSubject} from "rxjs/Rx";
import {HttpService} from "../../core/service/http.service";
import {CookieService} from "angular2-cookie/services/cookies.service";
import {Response} from "@angular/http";

@Injectable()
export class UserService {

    private readonly KEY_CURRENT_USER = 'cu';

    private _httpService: HttpService;

    private _cookieService: CookieService;

    private _userChange: BehaviorSubject<User>;

    public userChange$: Observable<User>;

    constructor(httpService: HttpService, cookieService: CookieService) {
        this._httpService = httpService;
        this._cookieService = cookieService;

        this._userChange = new BehaviorSubject<User>(null);
        this.userChange$ = this._userChange.asObservable();

        this.fetchCurrentUser();
    }

    public login(user: LoginUser): Observable<any> {
        let observable: Observable<any> = this._httpService.post('/users/login', user);

        observable.subscribe(() => this.fetchCurrentUser());
        return observable;
    }

    public getCurrentUser(): User {
        let userStr = localStorage.getItem(this.KEY_CURRENT_USER);
        if (!userStr) {
            return null;
        }
        return JSON.parse(userStr);
    }

    public update(user: User): void {
        let currentUser = this.getCurrentUser();
        if (!currentUser) {
            return;
        }

        // only update properties we want to update client-side
        let updateUser = new User();
        updateUser.workingHoursWeek = user.workingHoursWeek;

        this._httpService.put('/users', updateUser)
            .subscribe(() => this.fetchCurrentUser());
    }

    public logout(): Observable<Response> {
        let observable: Observable<Response> = this._httpService.post('/users/logout', null);
        observable.subscribe(
            () => {
                this.doLogout();
            }
        );
        return observable;
    }

    public register(user: User): Observable<any> {
        return this._httpService.post('/users', user);
    }

    private fetchCurrentUser(): void {
        this._httpService
            .get('/users/me')
            .subscribe(
                (res: Response) => {
                    if (res.status !== 200) {
                        this.doLogout()
                    } else {
                        let user = res.json();
                        localStorage.setItem(this.KEY_CURRENT_USER, JSON.stringify(user));
                        this._userChange.next(user);
                    }
                },
                () => this.doLogout()
            );
    }

    private doLogout() {
        this._cookieService.remove('TIMETRACK');
        localStorage.removeItem(this.KEY_CURRENT_USER);

        this._userChange.next(new User());
    }
}