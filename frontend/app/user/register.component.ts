import {Component, OnInit} from "@angular/core";
import {UserService} from "./service/user.service";
import {User} from "./model/user";
import {Router} from "@angular/router";

@Component({
    templateUrl: '/app/user/views/register.html'
})
export class RegisterComponent implements OnInit {

    private router: Router;

    private userService: UserService;

    user: User = new User();

    constructor(router: Router, userService: UserService) {
        this.router = router;
        this.userService = userService;
    }

    ngOnInit() {
        this.user = new User();
    }

    register() {
        this.userService.register(this.user)
            .subscribe(() => this.router.navigateByUrl("/login"));
    }
}