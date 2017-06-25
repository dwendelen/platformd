import {AfterViewInit, Component, ElementRef, NgZone} from "@angular/core";
import {LoginService} from "../login.service";
import {Subject} from "rxjs/Subject";

declare const gapi: any;

@Component({
  selector: 'google-signin',
  template: '<div class="g-signin2"></div><button *ngIf="isLoggedIn()" (click)="logout()">Logout</button>'
})
export class GoogleLoginComponent implements AfterViewInit {
  private clientId:string = '714940765820-4ronadar6r8rjgkn0lih986sgi14vl0o.apps.googleusercontent.com';

  private scope = [
    'profile',
    'email'
  ].join(' ');

  public auth2: any;

  public googleInit() {
    gapi.load('auth2', () => {
      this.auth2 = gapi.auth2.init({
        client_id: this.clientId,
        cookiepolicy: 'single_host_origin',
        scope: this.scope
      });
      this.attachSignin(this.element.nativeElement.firstChild);
    });
  }

  public attachSignin(element) {
    this.auth2.attachClickHandler(element, {},
      (googleUser) => {
        this.ngZone.run(() =>{
          this.loginService.login(googleUser.getAuthResponse().id_token);
        });
      }, function (error) {
        console.log(JSON.stringify(error, undefined, 2));
      });
  }

  public isLoggedIn(): boolean {
    return this.loginService.isLoggedIn()
  }

  public logout(): void {
    this.auth2.signOut().then(function () {
      console.log('User signed out.');
      this.ngZone.run(() => {
        this.loginService.logout();
      });
    });
  }

  constructor(
    private element: ElementRef,
    private loginService: LoginService,
    private ngZone: NgZone
  ) { }

  ngAfterViewInit() {
    this.googleInit();
  }
}
