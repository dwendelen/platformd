import * as React from 'react';
import {SimpleTransaction} from '../../core/transaction';
import {TransactionDate} from './date';
import {Comment} from './comment';
import {Amount} from './amount';
import {Account} from './account';
import {RowComponent, RowState} from './row';


class TransactionCompProps {
    transaction: SimpleTransaction;
}

export class SimpleTransactionState extends RowState {
    editing: boolean = false;
}

export class SimpleTransactionComp extends RowComponent<TransactionCompProps, SimpleTransactionState> {
    constructor() {
        super();
        this.state = new SimpleTransactionState();
    }

    rowClicked() {
        if (!this.state.editing) {
            this.updateState({
                editing: {
                    $set: true
                }
            });
        }
    }

    render() {
        let {date, otherAccount, amount, comment} = this.props.transaction;
        return (
            <div className="alpha grid_15 omega" onClick={() => this.rowClicked()}>
                <TransactionDate date={date} prefix={false} editing={this.state.editing}
                                 field={this.state.date}
                                 onChange={f => this.onDateChange(f)}/>
                <Account account={otherAccount} editing={this.state.editing}/>
                <Comment comment={comment} short={false} editing={this.state.editing}
                         field={this.state.comment}
                         onChange={f => this.onCommentChange(f)}/>
                <Amount amount={amount} suffix={false} editing={this.state.editing}
                        field={this.state.amount}
                        onChange={f => this.onAmountChange(f)}/>
            </div>
        );
    }
}