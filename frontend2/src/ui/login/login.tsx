import * as React from 'react';
import {connect, DispatchProp} from 'react-redux';
import {RouteComponentProps} from 'react-router';
import GoogleLogin, {GoogleLoginResponse} from 'react-google-login';
import {AppState, LoginState} from '../../core/AppModel';
import {store} from '../../core/store';

declare const gapi: any;

interface LoginProps {
    state: LoginState;
}

const mapStateToProps = (state: AppState, _: RouteComponentProps<{}>): LoginProps => ({
    state: state.loginState
});

export const logout = () => {
    let auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        store.dispatch({
            type: 'LOGGED_OUT'
        });
    });
};

class LoginComponentImpl extends React.Component<RouteComponentProps<{}> & DispatchProp<any> & LoginProps> {
    failed(_: any): void {}

    loggedIn(googleUser: GoogleLoginResponse): void {
        this.props.dispatch({
            type: 'LOGGED_IN',
            user: googleUser
        });
        this.props.history.push('/users/testuser');
    }

    render() {
        return (
            <GoogleLogin
                clientId="714940765820-4ronadar6r8rjgkn0lih986sgi14vl0o.apps.googleusercontent.com"
                onSuccess={(user: GoogleLoginResponse) => this.loggedIn(user)}
                onFailure={this.failed}
            />
        );
    }
}

export const LoginComponent = connect(mapStateToProps)(LoginComponentImpl);