import {GoogleSigninService} from "./google-signin.service";
import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {LoginService} from "./login.service";

@Component({
    selector: 'logout',
    template: '<a (click)="logout()">Log out</a>'
})
export class LogoutComponent implements OnInit {
    constructor(
        private googleService: GoogleSigninService,
        private loginService: LoginService,
        private router: Router
    ) {}

    ngOnInit(): void {
        this.loginService.loggedInObservable
            .subscribe(user => {
               if(user == null) {
                   this.router.navigate(['/loggedout']);
               }
            });
    }

    public logout(): void {
        this.googleService.logout();
    }
}
