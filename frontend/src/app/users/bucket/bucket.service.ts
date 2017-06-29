import {Injectable} from "@angular/core";
import {AuthenticatedHttp} from "../../http/http.service";
import {Observable} from "rxjs/Observable";
import "rxjs/add/observable/of";
import {Bucket} from "./bucket";
import {Summary} from "../summary/summary";


@Injectable()
export class BucketService {
    constructor(private http: AuthenticatedHttp) {
    }

    public getSummaries(userId: string): Observable<Summary<Bucket>[]> {
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
                name: "Bucket 1",
                balance: 123456.79,
                reference: Observable.of()
            },
            {
                name: "Bucket 2",
                balance: 123.45,
                reference: Observable.of()
            }
        ]);
    }
}
