import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginService} from "./login/login.service";
import {HttpModule} from "@angular/http";
import {AccountService} from "./users/account/account.service";
import {AuthenticatedHttp} from "./http/http.service";
import {AppRoutingModule} from "./app-routing.module";
import {LoginComponent} from "./login/login.component";
import {UserComponent} from "./users/user.component";
import {LogoutComponent} from "./login/logout.component";
import {GoogleSigninService} from "./login/google-signin.service";
import {LoggedOutComponent} from "./login/loggedout.component";

@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        AppRoutingModule,
    ],
    declarations: [
        AppComponent,
        LoginComponent,
        LogoutComponent,
        LoggedOutComponent,
        UserComponent,
    ],
    providers: [
        LoginService,
        AccountService,
        AuthenticatedHttp,
        GoogleSigninService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
