import {Action} from 'redux';
import {GoogleLoginResponse} from 'react-google-login';

export type LoginOrOutAction = LoginAction | LogoutAction

export interface LoginAction extends Action {
    user: GoogleLoginResponse
}

export interface LogoutAction extends Action {

}