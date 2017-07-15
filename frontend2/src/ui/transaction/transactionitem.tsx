import * as React from 'react';
import {Account} from './account';
import {RowComponent, RowState} from './row';
import {TransactionItemField} from './fields';
import {Field} from './field';

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
                <Field className="alpha prefix_1 grid_3"
                       editing={this.props.editing}
                       field={this.props.field.date}
                       onChange={f => this.onDateChange(f)}
                       autofocus/>
                <Account account={account}
                         editing={this.props.editing}/>
                <Field className="grid_4"
                       editing={this.props.editing}
                       field={this.props.field.comment}
                       onChange={f => this.onCommentChange(f)}/>
                <Field className="grid_2 suffix_1 omega currency"
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