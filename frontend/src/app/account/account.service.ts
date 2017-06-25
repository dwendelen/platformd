import {Injectable} from "@angular/core";
import {AuthenticatedHttp} from "../http/http.service";
import {Observable} from "rxjs/Observable";
import {Account} from "./account"

@Injectable()
export class AccountService {
  constructor(
    private http: AuthenticatedHttp
  ) {}

  getAccounts(userId: string): Observable<Account[]> {
    return this.http.get(`/api/users/${userId}/accounts`)
      .map(resp => resp.json() as Account[])
      .map(accounts => {
        if(accounts.length == 0) {
          return [{name: "empty"}];
        } else {
          return accounts;
        }
      })
  }
}
