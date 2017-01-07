var app = angular.module('platformd', ['ngResource']);
app.service('loginService', function () {
    var self = this;
    this.login = function (token) {
        this.token = token;
    };
    this.logout = function () {
        delete this.token;
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
app.config(function ($httpProvider) {
    $httpProvider.interceptors.push('headerInserter');
});

app.service('headerInserter', function (loginService) {
    this.request = function (config) {
        if (loginService.isLoggedIn()) {
            config.headers.Token = loginService.getToken();
        }
        return config;
    }
});
app.factory('Account', function ($resource) {
    return $resource("/api/accounts");
});
app.factory('Budget', function ($resource) {
    return $resource("/api/budget");
});
app.factory('Transaction', function ($resource) {
    return $resource("/api/accounts/:accountId/transactions");
});
app.controller('controller', function ($scope, loginService, Account, Budget, Transaction) {
    var self = this;
    function loadAccounts() {
        self.accounts = Account.query();
    }
    this.login = function (token) {
        loginService.login(token);
        self.loggedIn = true;
        loadAccounts();

        self.budgetItems = Budget.query();
    };
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
        newAccount.initialBalance = 0;
        newAccount.$save(loadAccounts());
    };
    this.getTransactions = function(account) {
        account.transactions = Transaction.query({accountId: account.uuid});
    };
});

function onSignIn(googleUser) {
    var id_token = googleUser.getAuthResponse().id_token;

    var element = angular.element($("#loginContainer"));
    var scope = element.scope();
    var controller = element.controller();

    scope.$apply(function () {
        controller.login(id_token);
    });
}

function onFailed(error) {
    console.log(error);
}