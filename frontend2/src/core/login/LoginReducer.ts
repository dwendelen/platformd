import {Reducer} from 'redux';
import {LoginState} from '../AppModel';
import {isUndefined} from 'util';
import {LoginAction, LoginOrOutAction} from './actions';

export const LoginReducer: Reducer<LoginState> =
    (state: LoginState, action: LoginOrOutAction): LoginState => {
        if (isUndefined(state)) {
            return new LoginState();
        }

        switch (action.type) {
            case 'LOGGED_IN':
                let loginAction = action as LoginAction;
                let googleUser = loginAction.user;
                let token = googleUser.getAuthResponse().id_token;

                return {
                    userId: 'userId',
                    token: token,
                    loggedIn: true,
                };
            case 'LOGGED_OUT':
                return {
                    userId: null,
                    token: null,
                    loggedIn: false
                };
            default:
                return state;
        }
    };