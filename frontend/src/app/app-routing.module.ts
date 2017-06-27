import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {OverviewComponent} from "./users/overview.component";
import {LoggedOutComponent} from "./login/loggedout.component";
import {DetailsComponent} from "./users/details.component";

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login',  component: LoginComponent },
  { path: 'loggedout', component: LoggedOutComponent },
  { path: 'users/:id', component: OverviewComponent },
  { path: 'details', component: DetailsComponent },
  //{ path: 'accountss/:id',  component: AccountComponent },
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}
