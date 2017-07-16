import * as React from 'react';
import {Account} from './account';
import {RowComponent, RowState} from './row';
import {TransactionItemField} from './fields';
import {AmountFieldComp, CommentFieldComp, DateFieldComp} from './field';

class ItemProps {
    editing: boolean;
    field: TransactionItemField;
    onTransactionItemChange: (newItem: TransactionItemField) => void;
}

export class TransactionItemComp extends RowComponent<ItemProps, RowState<TransactionItemField>, TransactionItemField> {
    render() {
        let account = 'acc';
        return (
            <div className="alpha grid_15 omega">
                <DateFieldComp
                    className="alpha prefix_1 grid_3"
                    editing={this.props.editing}
                    field={this.props.field.date}
                    onChange={f => this.onDateChange(f)}
                    autofocus={true}
                />
                <Account
                    account={account}
                    editing={this.props.editing}
                />
                <CommentFieldComp
                    className="grid_4"
                    editing={this.props.editing}
                    field={this.props.field.comment}
                    onChange={f => this.onCommentChange(f)}
                />
                <AmountFieldComp
                    className="grid_2 suffix_1 omega currency"
                    editing={this.props.editing}
                    field={this.props.field.amount}
                    onChange={f => this.onAmountChange(f)}
                />
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