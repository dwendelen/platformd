import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {UserComponent} from "./users/user.component";
import {LoggedOutComponent} from "./login/loggedout.component";

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login',  component: LoginComponent },
  { path: 'loggedout', component: LoggedOutComponent },
  { path: 'users/:id', component: UserComponent },
  //{ path: 'accounts/:id',  component: AccountComponent },
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}
