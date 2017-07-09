import * as React from 'react';
import {RouteComponentProps} from 'react-router';
import {Transaction} from '../core/transaction/transaction';
import {connect, DispatchProp} from 'react-redux';
import {AppState} from '../core/AppModel';
import {TransactionComp} from './transaction/transaction';
import {Summary} from '../core/summary';

export class DetailsImpl extends React.Component<RouteComponentProps<{}> & DispatchProp<any> & DetailsProps> {
    render() {
        let summaries = this.props.buckets.concat(this.props.accounts);
        return (
            <div>
                <div className="alpha grid_17 omega center">
                    <h1 className="no-top-margin">This is my account</h1>
                </div>
                <div className="alpha grid_15 suffix_2 omega transactions">
                    <div className="alpha grid_3">Date</div>
                    <div className="grid_4">Account / Bucket</div>
                    <div className="grid_6">Comment</div>
                    <div className="grid_2 omega">Amount</div>
                </div>
                <div className="alpha grid_15 suffix_2 omega transactions">
                    {this.props.transactions.map(transaction =>
                        <TransactionComp key={transaction.uuid} transaction={transaction} />
                    )}
                </div>
                <datalist id="destinations">
                    {summaries.map(s =>
                        <option key={s.uuid} value={s.name}/>
                    )}
                </datalist>
            </div>
        );
    }
}

interface DetailsProps {
    accounts: Summary[];
    buckets: Summary[];
    transactions: Transaction[];
}

const mapStateToProps = (state: AppState, _: RouteComponentProps<{}>): DetailsProps => ({
    transactions: state.accountDetails,
    accounts: state.accountSummaries,
    buckets: state.bucketSummaries
});

export const Details = connect(mapStateToProps)(DetailsImpl);