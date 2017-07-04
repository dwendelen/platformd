import * as React from 'react';
import {ComplexTransaction, SimpleTransaction, Transaction, TransactionItem} from '../../core/transaction';
import {TransactionDate} from './date';
import {Comment} from './comment';
import {Amount} from './amount';
import {Account} from './account';

export class TransactionComp extends React.Component<TransactionCompProps, TransactionCompState> {
    constructor() {
        super();
        this.state = {
            expanded: false,
            editing: false
        };
    }

    complexClicked(e: React.MouseEvent<HTMLDivElement>) {
        if(!this.state.editing) {
            this.setState({
                editing: false,
                expanded: !this.state.expanded
            });
        }
        e.stopPropagation()
    }

    rowClicked() {
        if (!this.state.editing) {
            this.setState({
                expanded: true,
                editing: true
            });
        }
    }

    render() {
        if (this.isComplex()) {
            return this.renderComplex(this.props.transaction as ComplexTransaction);
        } else {
            return this.renderSimple(this.props.transaction as SimpleTransaction);
        }
    }

    isComplex() {
        return 'otherItems' in this.props.transaction;
    }

    renderSimple(transaction: SimpleTransaction): JSX.Element {
        return (
            <div className="alpha grid_15 omega" onClick={() => this.rowClicked()}>
                {this.renderRow(transaction.date, transaction.otherAccount, transaction.comment, transaction.amount)}
            </div>
        );
    }

    renderRow(date: Date, account: string, comment: string, amount: number, subRow = false, isComplexHeader = false) {
        return [
            <TransactionDate date={date} prefix={subRow} editing={this.state.editing} />,
            this.getAccountRow(account, isComplexHeader),
            <Comment comment={comment} short={subRow} editing={this.state.editing} />,
            <Amount amount={amount} suffix={subRow} editing={this.state.editing} />
        ];
    }

    private getAccountRow(account: string, isComplexHeader: boolean) {
        if(isComplexHeader) {
            return (
                <div className="grid_4" onClick={e => this.complexClicked(e)}>
                    &lt;Complex&gt;
                </div>
            );
        } else {
            return <Account account={account} editing={this.state.editing}/>;
        }
    }

    renderComplex(transaction: ComplexTransaction): JSX.Element {
        let body: JSX.Element[] = [];
        if (this.state.expanded) {
            let bodies = transaction.otherItems.map(i => this.renderItem(i));
            body = this.flatten(bodies);
        }

        return (
            <div className="alpha grid_15 omega" onClick={() => this.rowClicked()}>
                {
                    this.renderHeaderComplex(transaction)
                        .concat(body)
                }
            </div>
        );
    }


    renderHeaderComplex(transaction: ComplexTransaction) {
        return this.renderRow(transaction.date, '<Complex>', transaction.comment, transaction.amount, false, true);
    }

    renderItem(item: TransactionItem) {
        return this.renderRow(item.date, item.account, item.comment, item.amount, true);
    }

    flatten<T>(list: T[][]): T[] {
        if (list.length === 0) {
            return [];
        }
        return list.reduce((a: T[], b: T[]) => a.concat(b), []);
    }
}

class TransactionCompProps {
    transaction: Transaction;
}

class TransactionCompState {
    expanded: boolean;
    editing: boolean;
}