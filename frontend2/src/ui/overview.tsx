import * as React from 'react';
import {RouteComponentProps} from 'react-router';
import {connect, DispatchProp} from 'react-redux';
import {AppState} from '../core/AppModel';
import {Summary} from '../core/summary';
import {SummaryComp} from './summary';

class PP {
    userId: string;
}

class OverviewImpl extends React.Component<RouteComponentProps<PP> & DispatchProp<any> & OverviewProps> {
    render() {
        return (
            <div>
                    <div id="accounts" className="alpha prefix_1 grid_6">
                        <h1 className="center no-top-margin">Accounts</h1>
                        {this.props.accounts.map(account =>
                            <SummaryComp summary={account}/>
                        )}
                    </div>
                    <div id="budget" className="prefix_1 grid_6 suffix_2 omega">
                        <h1 className="center no-top-margin">Buckets</h1>
                        {this.props.buckets.map(bucket =>
                            <SummaryComp summary={bucket}/>
                        )}
                    </div>
                </div>
        );
    }
}

const mapStateToProps = (state: AppState, _: RouteComponentProps<{}>): OverviewProps => {
    return {
        accounts: state.accountSummaries,
        buckets: state.bucketSummaries
    };
};

class OverviewProps {
    accounts: Summary[];
    buckets: Summary[];
}

export const Overview = connect(mapStateToProps)(OverviewImpl);