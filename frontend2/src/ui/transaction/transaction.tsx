import * as React from 'react';
import {ComplexTransaction, SimpleTransaction, Transaction} from '../../core/transaction';
import {SimpleTransactionComp} from './simpletransaction';
import {ComplexTransactionComp} from './complextransaction';

interface TransactionCompProps {
    transaction: Transaction;
}

export class TransactionComp extends React.Component<TransactionCompProps> {
    render() {
        if (this.isComplex()) {
            return <ComplexTransactionComp transaction={this.props.transaction as ComplexTransaction}/>;
        } else {
            return <SimpleTransactionComp transaction={this.props.transaction as SimpleTransaction}/>;
        }
    }

    isComplex() {
        return 'otherItems' in this.props.transaction;
    }
}