import * as React from 'react';
import {RouteComponentProps} from 'react-router';
import {ComplexTransaction, SimpleTransaction, Transaction} from '../core/transaction/transaction';
import {connect, DispatchProp} from 'react-redux';
import {AppState} from '../core/AppModel';
import {Summary} from '../core/summary';
import {ComplexTransactionComp} from './transaction/complextransaction';
import {SimpleTransactionComp} from './transaction/simpletransaction';

type PropsIn = RouteComponentProps<{}> & DispatchProp<{}> & DetailsProps;

export class DetailsImpl extends React.Component<PropsIn, DetailsState> {
    constructor() {
        super();
        this.state = {
            adding: false
        };
    }

    isComplex(transaction: Transaction) {
        return 'otherItems' in transaction;
    }

    addRow() {
        return;
    }

    render() {
        let summaries = this.props.buckets.concat(this.props.accounts);
        return (
            <div>
                <div className="alpha grid_17 omega center">
                    <h1 className="no-top-margin">This is my account</h1>
                </div>
                <div className="alpha grid_17 omega">
                    <button onClick={() => this.createClicked()}>Create</button>
                </div>
                <div className="alpha grid_15 suffix_2 omega transactions">
                    <div className="alpha grid_3">Date</div>
                    <div className="grid_4">Account / Bucket</div>
                    <div className="grid_6">Comment</div>
                    <div className="grid_2 omega">Amount</div>
                </div>
                {this.addRow()}
                <div className="alpha grid_15 suffix_2 omega transactions">
                    {
                        this.props.transactions.map(transaction => {
                            if (this.isComplex(transaction)) {
                                return <ComplexTransactionComp transaction={transaction as ComplexTransaction}/>;
                            } else {
                               return <SimpleTransactionComp transaction={transaction as SimpleTransaction}/>;
                            }
                        })
                    }
                </div>
                <datalist id="destinations">
                    {summaries.map(s =>
                        <option key={s.uuid} value={s.name}/>
                    )}
                </datalist>
            </div>
        );
    }

    private createClicked() {
        // eslint-disable-next-line
    }
}

interface DetailsProps {
    accounts: Summary[];
    buckets: Summary[];
    transactions: Transaction[];
}

interface DetailsState {
    adding: boolean;
}

const mapStateToProps = (state: AppState, _: RouteComponentProps<{}>): DetailsProps => ({
    transactions: state.accountDetails,
    accounts: state.accountSummaries,
    buckets: state.bucketSummaries
});

export const Details = connect(mapStateToProps)(DetailsImpl);