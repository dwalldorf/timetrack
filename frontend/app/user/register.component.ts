import {Component, OnInit, ViewChild, ElementRef} from "@angular/core";
import {UserService} from "./service/user.service";
import {User} from "./model/user";
import {Router} from "@angular/router";

@Component({
    templateUrl: '/app/user/views/register.html'
})
export class RegisterComponent implements OnInit {

    @ViewChild('usernameInput')
    private _usernameInput: ElementRef;

    private _router: Router;

    private _userService: UserService;

    private user: User = new User();

    constructor(router: Router, userService: UserService) {
        this._router = router;
        this._userService = userService;
    }

    ngOnInit() {
        this._usernameInput.nativeElement.focus();

        this.user = new User();
    }

    register() {
        this._userService.register(this.user)
            .subscribe(() => this._router.navigateByUrl("/login"));
    }
}