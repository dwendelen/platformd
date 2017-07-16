import * as React from 'react';
import {connect, DispatchProp} from 'react-redux';
import {RouteComponentProps} from 'react-router';
import GoogleLogin, {GoogleLoginResponse} from 'react-google-login';
import {AppState, LoginState} from '../../core/AppModel';
import {LoginAction} from '../../core/login/actions';

interface LoginProps {
    state: LoginState;
}

const mapStateToProps = (state: AppState, _: RouteComponentProps<{}>): LoginProps => ({
    state: state.loginState
});

class LoginComponentImpl extends React.Component<RouteComponentProps<{}> & DispatchProp<LoginAction> & LoginProps> {
    failed(_: {}): void  {
        // eslint-disable-next-line
    }

    loggedIn(googleUser: GoogleLoginResponse): void {
        this.props.dispatch({
            type: 'LOGGED_IN',
            user: googleUser
        });
        this.props.history.push('/users/testuser/budget');
    }

    render() {
        return (
            <div>
                <h1 className="no-top-margin">Login</h1>
                <GoogleLogin
                    clientId="714940765820-4ronadar6r8rjgkn0lih986sgi14vl0o.apps.googleusercontent.com"
                    onSuccess={(user: GoogleLoginResponse) => this.loggedIn(user)}
                    onFailure={this.failed}
                />
            </div>
        );
    }
}

export const LoginComponent = connect(mapStateToProps)(LoginComponentImpl);