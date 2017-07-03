import {Reducer} from 'redux';
import {AppState} from '../AppModel';
import {GoogleLoginResponse} from 'react-google-login';

export const LoginReducer: Reducer<AppState> =
    (state: AppState, action: any): AppState => {
        switch (action.type) {
            case 'LOGGED_IN':
                let googleUser = action.user as GoogleLoginResponse;
                let token = googleUser.getAuthResponse().id_token;

                return {
                    ...state,
                    loginState: {
                        userId: 'userId',
                        token: token,
                        loggedIn: true,
                    }
                };
            case 'LOGGED_OUT':
                return {
                    ...state,
                    loginState: {
                        userId: null,
                        token: null,
                        loggedIn: false,
                    }
                };
            default:
                return state;
        }
    };