import {Injectable} from "@angular/core";
import {User} from "../model/user";
import {HttpService} from "../../core/service/http.service";

@Injectable()
export class UserService {

    private httpService: HttpService;

    constructor(httpService: HttpService) {
        this.httpService = httpService;
    }

    getCurrentUser() {
        return this.httpService.get('http://localhost:8080/users/me');
    }

    login(user: User) {
        return this.httpService.post('http://localhost:8080/users/login', user);
    }

    logout() {
        return this.httpService.delete('http://localhost:8080/users/logout');
    }
}