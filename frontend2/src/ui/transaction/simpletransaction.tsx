import * as React from 'react';
import {SimpleTransaction} from '../../core/transaction/transaction';
import {Account} from './account';
import {TransactionRowComponent, TransactionRowState} from './row';
import {connect, DispatchProp} from 'react-redux';
import {AmountField, CommentField, DateField, SimpleTransactionField} from './fields';
import {DateFieldComp, CommentFieldComp, AmountFieldComp} from './field';
import {UpdateSimpleTransaction} from '../../core/transaction/actions';

interface TransactionCompProps {
    transaction: SimpleTransaction;
}

type Props = TransactionCompProps & DispatchProp<UpdateSimpleTransaction>;

class SimpleTransactionCompImpl extends
    TransactionRowComponent<Props, TransactionRowState<SimpleTransactionField>, SimpleTransactionField> {

    constructor(props: Props) {
        super(props);

        this.state = {
            editing: false,
            field: this.createField(props)
        };
    }

    componentWillReceiveProps(newProps: TransactionCompProps) {
        if (newProps.transaction !== this.props.transaction) {
            this.updateField(this.createField(newProps));
        }
    }

    createField(props: TransactionCompProps) {
        const dateField = new DateField(props.transaction.date);
        const commentField = new CommentField(props.transaction.comment);
        const amountField = new AmountField(props.transaction.amount);

        return new SimpleTransactionField(props.transaction.uuid, dateField, commentField, amountField);
    }

    render() {
        let {otherAccount} = this.props.transaction;
        return (
            <div
                className="alpha grid_15 omega"
                onClick={() => this.rowClicked()}
                onKeyDown={e => this.keyPressed(e)}
            >
                <DateFieldComp
                    className="alpha grid_3"
                    editing={this.state.editing}
                    field={this.state.field.date}
                    onChange={f => this.onDateChange(f)}
                    autofocus={true}
                />
                <Account
                    account={otherAccount}
                    editing={this.state.editing}
                />
                <CommentFieldComp
                    className="grid_6"
                    editing={this.state.editing}
                    field={this.state.field.comment}
                    onChange={f => this.onCommentChange(f)}
                />
                <AmountFieldComp
                    className="grid_2 omega currency"
                    editing={this.state.editing}
                    field={this.state.field.amount}
                    onChange={f => this.onAmountChange(f)}
                />
            </div>
        );
    }
}

const stateToProps = (state: {}, props: TransactionCompProps) => {
    return {};
};

export const SimpleTransactionComp = connect(stateToProps)(SimpleTransactionCompImpl);