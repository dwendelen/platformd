import * as React from 'react';

import {TransactionDate} from './date';
import {Amount} from './amount';
import {Comment} from './comment';
import {Account} from './account';
import {RowComponent, RowState} from './row';
import {TransactionItemField} from './field';

class ItemProps {
    editing: boolean;
    field: TransactionItemField;
    onTransactionItemChange: (newItem: TransactionItemField) => void;
}

export class TransactionItemComp extends RowComponent<ItemProps, RowState<TransactionItemField>, TransactionItemField> {
    render() {
        //let { account } = this.props.item;
        let account = 'acc';
        return (
            <div className="alpha grid_15 omega">
                <TransactionDate prefix={true}
                                 editing={this.props.editing}
                                 field={this.props.field.date}
                                 onChange={e => this.onDateChange(e)} />
                <Account account={account}
                         editing={this.props.editing}/>
                <Comment short={true}
                         editing={this.props.editing}
                         field={this.props.field.comment}
                         onChange={e => this.onCommentChange(e)} />
                <Amount suffix={true}
                        editing={this.props.editing}
                        field={this.props.field.amount}
                        onChange={f => this.onAmountChange(f)}/>
            </div>
        );
    }

    getField() {
        return this.props.field;
    }

    updateField(newField: TransactionItemField) {
        this.props.onTransactionItemChange(newField);
    }
}