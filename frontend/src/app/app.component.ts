import {Component, OnInit} from '@angular/core';
import {LoginService} from "./login.service";
import {Observable} from "rxjs/Observable";
import {AccountService} from "./account/account.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  accounts: Observable<Account[]>;

  constructor(
    private loginService: LoginService,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.accounts = this.loginService.loggedInObservable
      .switchMap(user => this.accountService.getAccounts(user))
      .share()
  }
}


/*
 app.controller('controller', function ($scope, loginService, Account, Budget, Transaction) {
 var self = this;

 this.login = function (token) {
 loginService.login(token, onLoggedIn);
 };
 function onLoggedIn() {
 self.loggedIn = true;
 loadAccounts();

 self.budgetItems = Budget.query({userId: loginService.getUserId()});
 }
 function loadAccounts() {
 self.accounts = Account.query({userId: loginService.getUserId()});
 }

 this.logout = function() {
 gapi.auth2.getAuthInstance().signOut();
 loginService.logout();
 self.loggedIn = false;

 self.budgetItems = [];
 self.accounts = [];
 };
 this.createAccount = function (name) {
 var newAccount = new Account();
 newAccount.name = name;
 newAccount.userId = loginService.getUserId();
 newAccount.balance = 0;
 newAccount.$save(function (newAcc) {
 self.accounts.push(newAcc);
 });
 };
 this.createBudget = function (name) {
 var budget = new Budget();
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
