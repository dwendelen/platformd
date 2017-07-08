import * as React from 'react';

import {TransactionItem} from '../../core/transaction';
import {TransactionDate} from './date';
import {Amount} from './amount';
import {Comment} from './comment';
import {Account} from './account';
import {RowComponent, RowState} from './row';

export class TransactionItemField extends RowState {
    id: number;
}

class ItemProps {
    editing: boolean;
    item: TransactionItem;
    onTransactionItemChange: (newItem: TransactionItemField) => void;
}

export class TransactionItemComp extends RowComponent<ItemProps, RowState > {
    constructor() {
        super();
        this.state = new RowState();
    }

    render() {
        let {date, account, amount, comment} = this.props.item;
        return (
            <div className="alpha grid_15 omega">
                <TransactionDate date={date} prefix={true} editing={this.props.editing}
                                 field={this.state.date}
                                 onChange={e => this.onDateChange(e)} />
                <Account account={account} editing={this.props.editing}/>
                <Comment comment={comment} short={true} editing={this.props.editing}
                         field={this.state.comment}
                         onChange={e => this.onCommentChange(e)} />
                <Amount amount={amount} suffix={true} editing={this.props.editing}
                        field={this.state.amount}
                        onChange={f => this.onAmountChange(f)}/>
            </div>
        );
    }

    onUpdated(tif: TransactionItemField) {
        this.props.onTransactionItemChange(tif);
    }
}