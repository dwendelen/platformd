import {ElementRef, Injectable, NgZone} from "@angular/core";
import {LoginService} from "./login.service";
import {Subject} from "rxjs/Subject";
import "rxjs/add/operator/single"
import "rxjs/add/operator/shareReplay"
import {Observable} from "rxjs/Observable";

declare const gapi: any;

@Injectable()
export class GoogleSigninService {
    private clientId: string = '714940765820-4ronadar6r8rjgkn0lih986sgi14vl0o.apps.googleusercontent.com';

    private loaded = new Subject<any>();
    private holder: Observable<any>;

    constructor(
        private zone: NgZone,
        private loginService: LoginService
    ) {
        this.holder = this.loaded
            .single()
            .shareReplay();
        gapi.load('auth2', () => {
            let auth2 = gapi.auth2.init({
                client_id: this.clientId,
                cookiepolicy: 'single_host_origin',
                scope: 'profile'
            });

            auth2.currentUser.listen(googleUser => {
                this.zone.run(() => {
                    this.loginService.login(googleUser.getAuthResponse().id_token);
                });
            });

            this.loaded.next(auth2);
            this.loaded.complete();
        });
    }

    public attachSignin(element: ElementRef): void {
        this.holder
            .subscribe(auth2 => {
                auth2.attachClickHandler(element.nativeElement.firstChild);
            });
    }

    public logout(): void {
        this.holder
            .subscribe(auth2 => {
                auth2.signOut().then(() => {
                    this.zone.run(() => {
                        this.loginService.logout();
                    });
                });
            });
    }
}
