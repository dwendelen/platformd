import * as React from 'react';
import {SimpleTransaction} from '../../core/transaction/transaction';
import {TransactionDate} from './date';
import {Comment} from './comment';
import {Amount} from './amount';
import {Account} from './account';
import {TransactionRowComponent, TransactionRowState} from './row';
import {connect, DispatchProp} from 'react-redux';


class TransactionCompProps {
    transaction: SimpleTransaction;
}

class SimpleTransactionCompImpl extends TransactionRowComponent<TransactionCompProps & DispatchProp<any>, TransactionRowState> {
    saveChanges(): void {
        let changes = [];
        if(this.state.date.newValue !== null && this.state.date.newValue !== this.props.transaction.date) {
            changes.push({
                type: 'UPDATE_DATE',
                newDate: this.state.date.newValue
            })
        }
        if(this.state.comment.newValue !== null && this.state.comment.newValue !== this.props.transaction.comment) {
            changes.push({
                type: 'UPDATE_COMMENT',
                newComment: this.state.comment.newValue
            })
        }
        if(this.state.amount.newValue !== null && this.state.amount.newValue !== this.props.transaction.amount) {
            changes.push({
                type: 'UPDATE_AMOUNT',
                newAmount: this.state.amount.newValue
            })
        }
        if(changes.length != 0) {
            this.props.dispatch({
                type: "UPDATE_SIMPLE_TRANSACTION",
                uuid: this.props.transaction.uuid,
                changes: changes
            })
        }
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

const stateToProps = (state:{}, props: TransactionCompProps) => { return {}};

export const SimpleTransactionComp = connect(stateToProps)(SimpleTransactionCompImpl);