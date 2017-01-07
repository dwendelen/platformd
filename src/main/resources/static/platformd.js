var app = angular.module('platformd', ['ngResource']);
app.service('loginService', function () {
    var self = this;
    this.login = function (token) {
        this.token = token;
    };
    this.getToken = function () {
        if(!self.isLoggedIn()) {
            throw "Not logged in";
        }
        return this.token;
    };
    this.isLoggedIn = function () {
        return this.token !== undefined;
    }
});

app.service('resourceProvider', function ($resource, loginService) {
    function resourceProperties() {
        return {
            'query': {
                isArray: true,
                headers: { 'Token': loginService.getToken() }
            },
            'save': {
                method: "POST",
                headers: { 'Token': loginService.getToken() }
            }
        }
    }

    this.getAccount = function() {
        if(this.account === undefined) {
            this.account = $resource("/api/accounts", {}, resourceProperties());
        }
        return this.account;
    };
    this.getTransaction = function() {
        if(this.transaction === undefined) {
            this.transaction = $resource("/api/accounts/:accountId/transactions", {}, resourceProperties());
        }
        return this.transaction;
    };
    this.getBudget = function() {
        if(this.budget === undefined) {
            this.budget = $resource("/api/budget", {}, resourceProperties());
        }
        return this.budget;
    };
});
app.controller('controller', function ($scope, loginService, resourceProvider) {
    var self = this;
    function loadAccounts() {
        self.accounts = resourceProvider.getAccount().query();
    }
    this.login = function (token) {
        self.token = token;
        loginService.login(token);

        loadAccounts();

        self.budgetItems = resourceProvider.getBudget().query();
    };
    this.createAccount = function (name) {
        var Account = resourceProvider.getAccount();
        var newAccount = new Account();
        newAccount.name = name;
        newAccount.initialBalance = 0;
        newAccount.$save(loadAccounts());
    };
    this.getTransactions = function(account) {
        account.transactions = resourceProvider.getTransaction().query({accountId: account.uuid});
    }
});

function onSignIn(googleUser) {
    var id_token = googleUser.getAuthResponse().id_token;

    var element = angular.element($("#loginContainer"));
    var scope = element.scope();
    var controller = element.controller();

    scope.$apply(function () {
        controller.login(id_token)
    });
}

function onFailed(error) {
    console.log(error);
}