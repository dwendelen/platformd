import * as React from 'react';
import {SimpleTransaction} from '../../core/transaction/transaction';
import {TransactionDate} from './date';
import {Comment} from './comment';
import {Amount} from './amount';
import {Account} from './account';
import {TransactionRowComponent, TransactionRowState} from './row';
import {connect, DispatchProp} from 'react-redux';
import {AmountField, CommentField, DateField, SimpleTransactionField} from './field';


interface TransactionCompProps {
    transaction: SimpleTransaction;
}

class SimpleTransactionCompImpl extends TransactionRowComponent<TransactionCompProps & DispatchProp<any>, TransactionRowState<SimpleTransactionField>, SimpleTransactionField> {
    constructor(props: TransactionCompProps & DispatchProp<any>) {
        super();
        const dateField = new DateField(props.transaction.date);
        const commentField = new CommentField(props.transaction.comment);
        const amountField = new AmountField(props.transaction.amount);

        this.state =  {
            editing: false,
            field: new SimpleTransactionField(props.transaction.uuid, dateField, commentField, amountField)
        };
    }

    render() {
        let {otherAccount} = this.props.transaction;
        return (
            <div className="alpha grid_15 omega"
                 onClick={() => this.rowClicked()}
                 onKeyDown={e => this.keyPressed(e)}>
                <TransactionDate prefix={false}
                                 editing={this.state.editing}
                                 autofocus
                                 field={this.state.field.date}
                                 onChange={f => this.onDateChange(f)}/>
                <Account account={otherAccount}
                         editing={this.state.editing}/>
                <Comment short={false}
                         editing={this.state.editing}
                         field={this.state.field.comment}
                         onChange={f => this.onCommentChange(f)}/>
                <Amount suffix={false}
                        editing={this.state.editing}
                        field={this.state.field.amount}
                        onChange={f => this.onAmountChange(f)}/>
            </div>
        );
    }
}

const stateToProps = (state:{}, props: TransactionCompProps) => { return {}};

export const SimpleTransactionComp = connect(stateToProps)(SimpleTransactionCompImpl);