import * as React from 'react';

import {TransactionItem} from '../../core/transaction';
import {Field} from './field';
import {TransactionDate} from './date';
import {Amount} from './amount';
import {Comment} from './comment';
import {Account} from './account';

export interface TransactionItemField {
    id: number;
    date: Field<Date>;
    comment: Field<string>;
}

interface TransactionCompProps {
    editing: boolean
    item: TransactionItem;
    field: TransactionItemField;
    onDateChange: (newDate: Field<Date>) => void
    onCommentChange: (nowComment: Field<string>) => void
}

export class TransactionItemComp extends React.Component<TransactionCompProps> {
    render() {
        let {date, account, amount, comment} = this.props.item;
        return (
            <div className="alpha grid_15 omega">
                <TransactionDate date={date} prefix={true} editing={this.props.editing} field={this.props.field.date} onChange={this.props.onDateChange} />
                <Account account={account} editing={this.props.editing}/>
                <Comment comment={comment} short={true} editing={this.props.editing} field={this.props.field.comment} onChange={this.props.onCommentChange} />
                <Amount amount={amount} suffix={true} editing={this.props.editing} />
            </div>
        );
    }
}