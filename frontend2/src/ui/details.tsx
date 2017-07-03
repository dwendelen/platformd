import * as React from 'react';
import {RouteComponentProps} from 'react-router';
import {Transaction} from '../core/transaction';
import {connect, DispatchProp} from 'react-redux';
import {AppState} from '../core/AppModel';
import {TransactionComp} from './transaction';

export class DetailsImpl extends React.Component<RouteComponentProps<{}> & DispatchProp<any> & DetailsProps> {
    render() {
        return (
            <div>
                <div className="alpha grid_18 omega center">
                    <h1 className="no-top-margin">This is my account</h1>
                </div>
                <div className="alpha prefix_1 grid_16 suffix_1 omega transactions">
                    {this.props.transactions.map(transaction =>
                        <TransactionComp transaction={transaction} />
                    )}
                </div>
            </div>
        );
    }
}

interface DetailsProps {
    transactions: Transaction[];
}

const mapStateToProps = (state: AppState, _: RouteComponentProps<{}>): DetailsProps => ({
    transactions: state.accountDetails
});

export const Details = connect(mapStateToProps)(DetailsImpl);