import {Reducer} from 'redux';
import {LoginState} from '../AppModel';
import {GoogleLoginResponse} from 'react-google-login';
import {isUndefined} from 'util';

export const LoginReducer: Reducer<LoginState> =
    (state: LoginState, action: any): LoginState => {
        if(isUndefined(state)) {
            return new LoginState()
        }

        switch (action.type) {
            case 'LOGGED_IN':
                let googleUser = action.user as GoogleLoginResponse;
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