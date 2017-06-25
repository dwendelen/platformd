import {AfterViewInit, Component, ElementRef, NgZone, OnInit} from "@angular/core";
import {LoginService} from "./login.service";
import {Router} from "@angular/router";
import {GoogleSigninService} from "./google-signin.service";
import {element} from "protractor";

@Component({
  selector: 'google-signin',
  template: '<div class="g-signin2"></div>'
})
export class LoginComponent implements OnInit, AfterViewInit {
    constructor(private element: ElementRef,
                private signInService: GoogleSigninService,
                private loginService: LoginService,
                private router: Router) {
    }

    ngOnInit(): void {
        this.loginService.loggedInObservable
            .subscribe(userId => {
                if(userId != null) {
                    this.router.navigate(['/users', userId])
                }
            });
    }

    ngAfterViewInit() {
        this.signInService.attachSignin(this.element);
    }
}
