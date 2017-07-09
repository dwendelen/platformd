import * as React from 'react';
import {SummaryComp} from './summary';
import {BrowserRouter, Link} from 'react-router-dom';
import {Route} from 'react-router';
import {LoginComponent} from './login/login';
import {AppState} from '../core/AppModel';
import {connect, DispatchProp} from 'react-redux';
import {Details} from './details';
import {Summary} from '../core/summary';
import {Budget} from './budget';
import {LogoutComponent} from './login/logout';

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
        let accounts = () => this.props.accounts.map(account =>
            <SummaryComp key={account.uuid} summary={account}/>
        );

        let nav;
        if (this.props.loggedIn) {
            nav = (
                <div id="nav" className="grid_6">
                    <h1 className="no-top-margin">Menu</h1>
                    <Link to={`/users/${this.props.userId}/budget`}><div className="alpha grid_6 omega">Budget</div></Link>
                    {accounts()}
                    <LogoutComponent />
                </div>
            );
        } else {
            nav = (
                <div id="nav" className="grid_6">
                    <h1 className="no-top-margin">Menu</h1>
                    <Link to="/login"><div className="alpha grid_6 omega">Login</div></Link>
                </div>
            );
        }

        return (
            <BrowserRouter>
                <div>
                    <div className="header"/>
                    <div className="container_24">
                        {nav}
                        <div id="content" className="prefix_1 grid_17">
                            <Route exact path="/" component={LoginComponent}/>
                            <Route exact path="/login" component={LoginComponent}/>
                            <Route exact path="/users/:userId/budget" component={Budget}/>
                            <Route exact path="/accounts/:uuid" component={Details}/>
                        </div>
                    </div>
                </div>
            </BrowserRouter>
        );
    }
}

export const App = connect(mapStateToProps)(AppImpl);