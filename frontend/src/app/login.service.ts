import {Http} from "@angular/http";
import {Injectable, OnInit} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Subject} from "rxjs/Subject";

import "rxjs/add/operator/switchMap"
import "rxjs/add/operator/map"
import "rxjs/add/operator/do"
import "rxjs/add/operator/share"

@Injectable()
export class LoginService {
  private user: LoginResponse = null;
  private loginSubject = new Subject<string>();

  loggedInObservable: Observable<string>;

  constructor(
    private http: Http
  ) {
    let loginObservable =


    this.loggedInObservable = this.loginSubject
      .switchMap(token => {
        return this.http.post('api/auth/login', token)
      })
      .map(resp => resp.json() as LoginResponse)
      .do(r => this.user = r)
      .map(resp => resp.userId)
      .share();

      this.loggedInObservable.subscribe();
  }

  login(googleToken): void {
    this.loginSubject.next(googleToken)
  }

  getUserId(): string {
    return this.user.userId;
  }

  getToken(): string {
    return this.user.token;
  }

  logout(): void {
    this.user = null;
  }

  isLoggedIn(): boolean {
    return this.user !== null;
  }
}

export class LoginResponse {
  userId: string;
  isAdmin: boolean;
  token: string
}
