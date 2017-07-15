import * as React from 'react';

import {TransactionItem} from '../../core/transaction/transaction';
import {TransactionDate} from './date';
import {Amount} from './amount';
import {Comment} from './comment';
import {Account} from './account';
import {RowComponent, RowState} from './row';
import {AmountField, CommentField, DateField, TransactionItemField} from './field';

class ItemProps {
    editing: boolean;
    item: TransactionItem;
    onTransactionItemChange: (newItem: TransactionItemField) => void;
}

export class TransactionItemComp extends RowComponent<ItemProps, RowState<TransactionItemField>, TransactionItemField> {
    constructor(props: ItemProps) {
        super();
        const dateField = new DateField(props.item.date);
        const commentField = new CommentField(props.item.comment);
        const amountField = new AmountField(props.item.amount);

        this.state = {
            field: new TransactionItemField(props.item.id, dateField, commentField, amountField)
        }
    }

    render() {
        let { account} = this.props.item;
        return (
            <div className="alpha grid_15 omega">
                <TransactionDate prefix={true}
                                 editing={this.props.editing}
                                 field={this.state.field.date}
                                 onChange={e => this.onDateChange(e)} />
                <Account account={account}
                         editing={this.props.editing}/>
                <Comment short={true}
                         editing={this.props.editing}
                         field={this.state.field.comment}
                         onChange={e => this.onCommentChange(e)} />
                <Amount suffix={true}
                        editing={this.props.editing}
                        field={this.state.field.amount}
                        onChange={f => this.onAmountChange(f)}/>
            </div>
        );
    }

    onUpdated(newField: TransactionItemField) {
        this.props.onTransactionItemChange(newField);
    }
}