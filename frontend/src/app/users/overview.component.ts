import {Component, OnInit} from "@angular/core";
import {LoginService} from "../login/login.service";
import {AccountService} from "./account/account.service";
import {Account} from "./account/account";
import {Observable} from "rxjs/Observable";

@Component({
    templateUrl: './overview.component.html'
})
export class OverviewComponent implements OnInit {
    accountss: Observable<Account[]>;
    name: String;

    constructor(private loginService: LoginService,
                private accountService: AccountService) {
    }

    ngOnInit(): void {
        let userId = this.loginService.getUserId();
        this.accountss = this.accountService.getAccounts(userId);
        this.name = this.loginService.getName();
    }
}
