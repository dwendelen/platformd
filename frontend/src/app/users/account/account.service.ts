import {Injectable} from "@angular/core";
import {AuthenticatedHttp} from "../../http/http.service";
import {Observable} from "rxjs/Observable";
import {Account} from "./account";
import "rxjs/add/observable/of";


@Injectable()
export class AccountService {
    constructor(private http: AuthenticatedHttp) {
    }

    public getAccounts(userId: string): Observable<Account[]> {
        /*return this.http.get(`/api/users/${userId}/accounts`)
         .map(resp => resp.json() as Account[])
         .map(accounts => {
         if(accounts.length == 0) {
         return [{name: "empty"}];
         } else {
         return accounts;
         }
         })*/
        let account: Account = {
            userId: userId,
            name: "Account 1",
            balance: 2345.23,
            accountId: "oentheu"
        };
        return Observable.of([
                account
            ]);
    }
}
