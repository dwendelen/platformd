import * as React from 'react';
import {SummaryComp} from './summary';
import {BrowserRouter, Link} from 'react-router-dom';
import {Route} from 'react-router';
import {LoginComponent, logout} from './login/login';
import {AppState} from '../core/AppModel';
import {connect, DispatchProp} from 'react-redux';
import {Overview} from './overview';
import {Details} from './details';
import {Summary} from '../core/summary';

interface AppProps {
    loggedIn: boolean;
    userId: string | null;
    accounts: Summary[];
}

const mapStateToProps = (state: AppState): AppProps => ({
    loggedIn: state.loginState.loggedIn,
    userId: state.loginState.userId,
    accounts: state.accountSummaries
});

class AppImpl extends React.Component<DispatchProp<{}> & AppProps> {
    render(): JSX.Element {
        let accounts = this.props.accounts.map(account =>
            <SummaryComp summary={account}/>
        );

        let nav;
        if (this.props.loggedIn) {
            nav = [<div><Link to={`/users/${this.props.userId}`} className="navlink">Overview</Link></div>]
                .concat(accounts)
                .concat([
                    <div><a className="navlink" onClick={logout}>Logout</a></div>,
                ]);
        } else {
            nav = [<div><Link to="/login" className="navlink">Login</Link></div>];
        }

        return (
            <BrowserRouter>
                <div>
                    <div className="header"/>
                    <div className="container_24">
                        <div id="nav" className="grid_6">
                            <h1 className="no-top-margin">Menu</h1>
                            {nav}
                        </div>
                        <div id="content" className="grid_18">
                            <Route exact={true} path="/" component={LoginComponent}/>
                            <Route exact={true} path="/login" component={LoginComponent}/>
                            <Route exact={true} path="/users/:userId" component={Overview}/>
                            <Route exact={true} path="/details" component={Details}/>
                        </div>
                    </div>
                </div>
            </BrowserRouter>
        );
    }
}

export const App = connect(mapStateToProps)(AppImpl);