import {applyMiddleware, createStore} from 'redux';
import logger from 'redux-logger';
import {LoginReducer} from './login/LoginReducer';
import {AppState} from './AppModel';

import {transactions} from './data/transactions';
import {buckets} from './data/buckets';
import {accounts} from './data/accounts';

let initial: AppState = {
    loginState: {
        loggedIn: false,
        userId: null,
        token: null
    },
    accountSummaries: accounts,
    bucketSummaries: buckets,
    accountDetails: transactions
};

export const store = createStore(LoginReducer, initial, applyMiddleware(logger));