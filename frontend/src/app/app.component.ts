import {Component, OnInit} from '@angular/core';
import {LoginService} from "./login/login.service";
import {Observable} from "rxjs/Observable";
import {AccountService} from "./users/account/account.service";
import {Summary} from "./users/summary/summary";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    accountss: Observable<Summary<Account>[]>;

    constructor(private loginService: LoginService,
                private accountService: AccountService) {
    }

    /*ngOnInit(): void {
     this.accountss = this.loginService.loggedInObservable
     .switchMap(user => this.accountService.getAccounts(user))
     .share()
     }*/
    ngOnInit(): void {
        let userId = this.loginService.getUserId();
        this.accountss = this.accountService.getSummaries(userId);
    }

    loggedIn(): boolean {
        return this.loginService.isLoggedIn();
    }

    getUserId(): string {
        return this.loginService.getUserId();
    }
}


/*
 app.controller('controller', function ($scope, loginService, Account, Bucket, Transaction) {
 var self = this;

 this.login = function (token) {
 loginService.login(token, onLoggedIn);
 };
 function onLoggedIn() {
 self.loggedIn = true;
 loadAccounts();

 self.budgetItems = Bucket.query({userId: loginService.getUserId()});
 }
 function loadAccounts() {
 self.accountss = Account.query({userId: loginService.getUserId()});
 }

 this.logout = function() {
 gapi.auth2.getAuthInstance().signOut();
 loginService.logout();
 self.loggedIn = false;

 self.budgetItems = [];
 self.accountss = [];
 };
 this.createAccount = function (name) {
 var newAccount = new Account();
 newAccount.name = name;
 newAccount.userId = loginService.getUserId();
 newAccount.balance = 0;
 newAccount.$save(function (newAcc) {
 self.accountss.push(newAcc);
 });
 };
 this.createBudget = function (name) {
 var budget = new Bucket();
 budget.name = name;
 budget.userId = loginService.getUserId();
 budget.$save(function (newBudget) {
 self.budgetItems.push(newBudget);
 });
 };
 this.clickedOnAccount = function(account) {
 if(account.transactions === undefined) {
 account.transactions = Transaction.query({accountId: account.accountId});
 }
 account.showTransactions = !account.showTransactions;
 };
 this.addTransaction = function(account, timestamp, amount, comment, budgetItem) {
 var trans = new Transaction({accountId: account.accountId});
 trans.transactionDate = timestamp;
 trans.amount = amount;
 trans.comment = comment;
 trans.budgetItem = budgetItem;

 trans.$save(function() {
 account.transactions.push(trans);
 account.balance += Number(trans.amount);
 });
 };
 });

 */
