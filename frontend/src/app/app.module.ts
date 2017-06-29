import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginService} from "./login/login.service";
import {HttpModule} from "@angular/http";
import {AccountService} from "./users/account/account.service";
import {AuthenticatedHttp} from "./http/http.service";
import {AppRoutingModule} from "./app-routing.module";
import {LoginComponent} from "./login/login.component";
import {OverviewComponent} from "./users/overview.component";
import {LogoutComponent} from "./login/logout.component";
import {GoogleSigninService} from "./login/google-signin.service";
import {LoggedOutComponent} from "./login/loggedout.component";
import {BucketService} from "./users/bucket/bucket.service";
import {SummaryComponent} from "./users/summary/summary.component";
import {DetailsComponent} from "./users/details.component";
import {TransactionComponent} from "./users/transaction/transaction.component";

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
        OverviewComponent,
        SummaryComponent,
        DetailsComponent,
        TransactionComponent
    ],
    providers: [
        LoginService,
        AccountService,
        BucketService,
        AuthenticatedHttp,
        GoogleSigninService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
