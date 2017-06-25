import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {LoginService} from "./login.service";
import {HttpModule} from "@angular/http";
import {GoogleLoginComponent} from "./google/login.component";
import {AccountService} from "./account/account.service";
import {AuthenticatedHttp} from "./http/http.service";

@NgModule({
  declarations: [
    AppComponent,
    GoogleLoginComponent
  ],
  imports: [
    BrowserModule,
    HttpModule
  ],
  providers: [
    LoginService,
    AccountService,
    AuthenticatedHttp
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
