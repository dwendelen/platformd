import {Http} from "@angular/http";
import {Injectable, OnInit} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Subject} from "rxjs/Subject";

import "rxjs/add/operator/switchMap"
import "rxjs/add/operator/map"
import "rxjs/add/operator/do"
import "rxjs/add/operator/share"
import "rxjs/add/operator/delay"
import "rxjs/observable/of"
import {ArrayObservable} from "rxjs/observable/ArrayObservable";

@Injectable()
export class LoginService {
  private user: LoginResponse = null;
  private loginSubject = new Subject<string>();

  loggedInObservable: Observable<string>;

  constructor(
    private http: Http
  ) {
    this.loggedInObservable = this.loginSubject
      .switchMap(token => {
        if(token == null) {
          return ArrayObservable.of(null);
        }
        console.log("replacing " + token + " with dummy");
        //return this.http.post('api/auth/login', token)
        return ArrayObservable.of<LoginResponse>({
          userId: "myuserid",
          name: "thisismyname",
          token: "blotoken",
          isAdmin: true
        });
      })
      //.map(resp => resp.json() as LoginResponse)
      .do(r => this.user = r)
      .map(resp => resp == null? null: resp.userId)
      .share();

      this.loggedInObservable.subscribe();
  }

  login(googleToken): void {
    this.loginSubject.next(googleToken)
  }

  getUserId(): string {
    return this.user == null? null: this.user.userId;
  }

  getToken(): string {
    return this.user.token;
  }

  logout(): void {
    this.loginSubject.next(null);
  }

  isLoggedIn(): boolean {
    return this.user !== null;
  }

  getName(): string {
    return this.user == null? null: this.user.name;
  }
}

export class LoginResponse {
  userId: string;
  isAdmin: boolean;
  token: string;
  name: string;
}
