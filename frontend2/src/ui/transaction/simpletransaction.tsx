import * as React from 'react';
import {SimpleTransaction} from '../../core/transaction';
import {TransactionDate} from './date';
import {Comment} from './comment';
import {Amount} from './amount';
import {Account} from './account';
import {TransactionRowComponent, TransactionRowState} from './row';


class TransactionCompProps {
    transaction: SimpleTransaction;
}

export class SimpleTransactionComp extends TransactionRowComponent<TransactionCompProps, TransactionRowState> {
    saveChanges(): void {
        console.log("Saving changes")
    }

    constructor() {
        super();
        this.state = new TransactionRowState();
    }

    render() {
        let {date, otherAccount, amount, comment} = this.props.transaction;
        return (
            <div className="alpha grid_15 omega"
                 onClick={() => this.rowClicked()}
                 onKeyDown={e => this.keyPressed(e)}>
                <TransactionDate date={date} prefix={false} editing={this.state.editing}
                                 autofocus
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