import {applyMiddleware, combineReducers, createStore} from 'redux';
import logger from 'redux-logger';
import {LoginReducer} from './login/LoginReducer';
import {AppState} from './AppModel';

import {transactions} from './data/transactions';
import {buckets} from './data/buckets';
import {accounts} from './data/accounts';
import {TransactionReducer} from './transaction/TransactionReducer';
import {EchoReducer} from './EchoReducer';

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

let reducers = combineReducers({
    'loginState': LoginReducer,
    'accountDetails': TransactionReducer,
    'accountSummaries': EchoReducer(),
    'bucketSummaries': EchoReducer()
});

export const store = createStore(reducers, initial, applyMiddleware(logger));
