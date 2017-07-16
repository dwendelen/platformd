import * as React from 'react';
import {connect, DispatchProp} from 'react-redux';
import {AppState} from '../../core/AppModel';
import {RouteComponentProps, withRouter} from 'react-router';
import {LogoutAction} from '../../core/login/actions';

class LogoutComponentImpl extends React.Component<DispatchProp<LogoutAction> & RouteComponentProps<{}>> {
    logout() {
        let auth2 = gapi.auth2.getAuthInstance();
        auth2.signOut().then(() => {
            this.props.dispatch({
                type: 'LOGGED_OUT'
            });
            this.props.history.push('/login');
        });
    }

    render() {
        return (
            <a className="logout-link" onClick={() => this.logout()}>
                <div className="alpha grid_6 omega">Logout</div>
            </a>
        );
    }
}

const mapStateToProps = (app: AppState, route: RouteComponentProps<{}>) => ({});

export const LogoutComponent = withRouter<{}>(connect(mapStateToProps)(LogoutComponentImpl));