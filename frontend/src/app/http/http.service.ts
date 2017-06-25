import {Injectable} from "@angular/core";
import {LoginService} from "../login/login.service";
import {Headers, Http, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class AuthenticatedHttp {
  constructor(
    private http: Http,
    private loginService: LoginService
  ) {}

  get(url: string): Observable<Response> {
    let headers = new Headers({'Content-Type': 'application/json'});
    if(this.loginService.isLoggedIn()) {
      headers.append("Token", this.loginService.getToken())
    }

    return this.http.get(url, {headers: headers})
  }
}
