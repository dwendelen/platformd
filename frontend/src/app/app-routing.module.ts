import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {OverviewComponent} from "./users/overview.component";
import {LoggedOutComponent} from "./login/loggedout.component";

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login',  component: LoginComponent },
  { path: 'loggedout', component: LoggedOutComponent },
  { path: 'users/:id', component: OverviewComponent },
  //{ path: 'accounts/:id',  component: AccountComponent },
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}
