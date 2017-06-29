import {Component, OnInit} from "@angular/core";
import {LoginService} from "../login/login.service";
import {AccountService} from "./account/account.service";
import {Account} from "./account/account";
import {Observable} from "rxjs/Observable";
import {BucketService} from "./bucket/bucket.service";
import {Bucket} from "./bucket/bucket";
import {Summary} from "./summary/summary";

@Component({
    templateUrl: './overview.component.html'
})
export class OverviewComponent implements OnInit {
    accountss: Observable<Summary<Account>[]>;
    budgetItems: Observable<Summary<Bucket>[]>;

    name: String;

    constructor(private loginService: LoginService,
                private accountService: AccountService,
                private budgetService: BucketService) {
    }

    ngOnInit(): void {
        let userId = this.loginService.getUserId();
        this.accountss = this.accountService.getSummaries(userId);
        this.budgetItems = this.budgetService.getSummaries(userId);
        this.name = this.loginService.getName();
    }
}
