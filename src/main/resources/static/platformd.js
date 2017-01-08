var app = angular.module('platformd', ['ngResource']);
app.service('tokenHolder', function () {});
app.service('loginService', function ($http, tokenHolder) {
    var self = this;
    this.login = function (googleToken, onSucces) {
        $http.post('api/auth/login', googleToken)
            .then(onTokenRecieved);
        function onTokenRecieved(response) {
            tokenHolder.token = response.data.token;
            self.user = response.data;
            onSucces();
        }
    };
    this.logout = function () {
        delete tokenHolder.token;
        delete self.user;
    };
    this.isLoggedIn = function () {
        return self.user !== undefined;
    };
});
app.config(function ($httpProvider) {
    $httpProvider.interceptors.push('headerInserter');
});

app.service('headerInserter', function (tokenHolder) {
    function addTokenToConfig (config) {
        if (tokenHolder.token !== undefined) {
            config.headers.Token = tokenHolder.token;
        }
        return config;
    }
    this.request = addTokenToConfig;
});
app.factory('Account', function ($resource) {
    return $resource("/api/accounts");
});
app.factory('Budget', function ($resource) {
    return $resource("/api/budget");
});
app.factory('Transaction', function ($resource) {
    return $resource("/api/accounts/:accountId/transactions", {accountId: '@accountId'});
});
app.controller('controller', function ($scope, loginService, Account, Budget, Transaction) {
    var self = this;

    this.login = function (token) {
        loginService.login(token, onLoggedIn);
    };
    function onLoggedIn() {
        self.loggedIn = true;
        loadAccounts();

        self.budgetItems = Budget.query();
    }
    function loadAccounts() {
        self.accounts = Account.query();
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
        newAccount.$save(function (newAcc) {
            self.accounts.push(newAcc);
        });
    };
    this.createBudget = function (name) {
        var budget = new Budget();
        budget.name = name;
        budget.$save(function (newBudget) {
           self.budgetItems.push(newBudget);
        });
    };
    this.clickedOnAccount = function(account) {
        if(account.transactions === undefined) {
            account.transactions = Transaction.query({accountId: account.uuid});
        }
        account.showTransactions = !account.showTransactions;
    };
    this.addTransaction = function(account, timestamp, amount, comment, budgetItem) {
        var trans = new Transaction({accountId: account.uuid});
        trans.timestamp = timestamp;
        trans.amount = amount;
        trans.comment = comment;
        trans.budgetItem = budgetItem;

        trans.$save(function() {
            account.transactions.push(trans);
            account.balance += Number(trans.amount);
        });
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