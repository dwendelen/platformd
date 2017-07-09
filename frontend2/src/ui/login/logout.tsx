import * as React from 'react';
import {connect, DispatchProp} from 'react-redux';
import {AppState} from '../../core/AppModel';
import {RouteComponentProps, withRouter} from 'react-router';

declare const gapi: any;

class LogoutComponentImpl extends React.Component<DispatchProp<any> & RouteComponentProps<{}>> {
    logout() {
        let auth2 = gapi.auth2.getAuthInstance();
        auth2.signOut().then(() => {
            this.props.dispatch({
                type: 'LOGGED_OUT'
            });
            this.props.history.push("/login")
        });
    }

    render() {
        return (
            <div><a className="navlink" onClick={() => this.logout()}>Logout</a></div>
        );
    }
}

const mapStateToProps = (app: AppState, route: RouteComponentProps<{}>) => ({});

export const LogoutComponent = withRouter<{}>(connect(mapStateToProps)(LogoutComponentImpl));