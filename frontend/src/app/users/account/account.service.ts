import {Injectable} from "@angular/core";
import {AuthenticatedHttp} from "../../http/http.service";
import {Observable} from "rxjs/Observable";
import {Account} from "./account";
import "rxjs/add/observable/of";
import {Summary} from "../summary/summary";


@Injectable()
export class AccountService {
    constructor(private http: AuthenticatedHttp) {
    }

    public getSummaries(userId: string): Observable<Summary<Account>[]> {
        /*return this.http.get(`/api/users/${userId}/accountss`)
         .map(resp => resp.json() as Account[])
         .map(accountss => {
         if(accountss.length == 0) {
         return [{name: "empty"}];
         } else {
         return accountss;
         }
         })*/
        return Observable.of([
            {
                name: "Account 1",
                balance: 123456.79,
                reference: Observable.of()
            },
            {
                name: "Account 2",
                balance: 123.45,
                reference: Observable.of()
            },
            {
                name: "Negative",
                balance: -123.45,
                reference: Observable.of()
            },
            {
                name: "Account with a very long name",
                balance: 123456.79,
                reference: Observable.of()
            },
            {
                name: "Account with a very long name",
                balance: 1.23,
                reference: Observable.of()
            },
        ]);
    }
}
