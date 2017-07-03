import * as React from 'react';
import {ComplexTransaction, SimpleTransaction, Transaction, TransactionItem} from '../core/transaction';
import moment = require('moment');

export class TransactionComp extends React.Component<TransactionCompProps, TransactionCompState> {
    constructor() {
        super();
        this.state = {
            expanded: false
        };
    }

    isComplex() {
        return 'otherItems' in this.props.transaction;
    }

    renderSimple(transaction: SimpleTransaction): JSX.Element {
        return (
            <div className="alpha grid_16 omega">
                <div className="alpha grid_3">
                    {moment(transaction.date).format('YYYY-MM-DD')}
                </div>
                <div className="grid_4">
                    {transaction.otherAccount}
                </div>
                <div className="grid_6">
                    {transaction.comment || <div>&nbsp;</div>}
                </div>
                <div className="grid_3 omega currency">
                    {transaction.amount}
                </div>
            </div>
        );
    }

    renderComplex(transaction: ComplexTransaction): JSX.Element {
        let body: JSX.Element[] = [];
        if (this.state.expanded) {
            let bodies = transaction.otherItems.map(this.renderItem);
            body = this.flatten(bodies);
        }
        let onClick = this.state.expanded ? () => this.collapse() : () => this.expand();

        return (
            <div className="alpha grid_16 omega" onClick={onClick}>
                {this.renderHeaderComplex(transaction).concat(body)}
            </div>
        );
    }

    expand() {
        this.setState({
            expanded: true
        });
    }

    collapse() {
        this.setState({
            expanded: false
        });
    }

    renderHeaderComplex(transaction: ComplexTransaction) {
        return [
            (
                <div className="alpha grid_3">
                    {moment(transaction.date).format('YYYY-MM-DD')}
                </div>
            ),
            (
                <div className="grid_4">
                    &lt;Complex&gt;
                </div>
            ),
            (
                <div className="grid_6">
                    {transaction.comment || <div>&nbsp;</div>}
                </div>
            ),
            (
                <div className="grid_3 omega currency">
                    {transaction.amount}
                </div>
            )
        ];
    }

    renderItem(item: TransactionItem) {
        return [
            (
                <div className="alpha prefix_1 grid_3">
                    {moment(item.date).format('YYYY-MM-DD')}
                </div>
            ),
            (
                <div className="grid_4">
                    {item.account}
                </div>
            ),
            (
                <div className="grid_4">
                    {item.comment || <div>&nbsp;</div>}
                </div>
            ),
            (
                <div className="grid_3 suffix_1 omega currency">
                    {item.amount}
                </div>
            )
        ];
    }

    flatten<T>(list: T[][]): T[] {
        if (list.length === 0) {
            return [];
        }
        return list.reduce((a: T[], b: T[]) => a.concat(b), []);
    }

    render() {
        if (this.isComplex()) {
            return this.renderComplex(this.props.transaction as ComplexTransaction);
        } else {
            return this.renderSimple(this.props.transaction as SimpleTransaction);
        }
    }
}

class TransactionCompProps {
    transaction: Transaction;
}

class TransactionCompState {
    expanded: boolean;
}