import {Component, OnInit} from "@angular/core";
import {UserService} from "../user/service/user.service";

@Component({
    templateUrl: '/app/dashboard/views/dashboard.html'
})
export class DashboardComponent implements OnInit {

    private userService: UserService;

    constructor(userService: UserService) {
        this.userService = userService;
    }

    ngOnInit() {
    }
}