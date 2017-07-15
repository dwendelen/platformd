import * as React from 'react';
import {ComplexTransaction} from '../../core/transaction/transaction';
import {TransactionItemComp} from './transactionitem';
import {TransactionRowComponent, TransactionRowState} from './row';
import {AmountField, CommentField, ComplexTransactionField, DateField, TransactionItemField} from './fields';
import {connect, DispatchProp} from 'react-redux';
import {Field} from './field';

class TransactionCompProps {
    transaction: ComplexTransaction;
}

interface ComplexTransactionState extends TransactionRowState<ComplexTransactionField> {
    expanded: boolean;
}

class ComplexTransactionCompImpl extends TransactionRowComponent<TransactionCompProps & DispatchProp<any>, ComplexTransactionState, ComplexTransactionField> {
    constructor(props: TransactionCompProps & DispatchProp<any>) {
        super(props);

        this.state = {
            expanded: false,
            editing: false,
            field: this.createField(props)
        };
    }

    componentWillReceiveProps(newProps: TransactionCompProps) {
        if (newProps.transaction != this.props.transaction) {
            this.updateField(this.createField(newProps));
        }
    }

    createField(props: TransactionCompProps) {
        const dateField = new DateField(props.transaction.date);
        const commentField = new CommentField(props.transaction.comment);
        const amountField = new AmountField(props.transaction.amount);

        let items = props.transaction.otherItems.map(item => {
            const dateField = new DateField(item.date);
            const commentField = new CommentField(item.comment);
            const amountField = new AmountField(item.amount);

            return new TransactionItemField(item.id, dateField, commentField, amountField);
        });

        return new ComplexTransactionField(props.transaction.uuid, dateField, commentField, amountField, items);
    }

    complexClicked(e: React.MouseEvent<HTMLDivElement>) {
        if (!this.state.editing) {
            this.updateState({
                expanded: {
                    $set: !this.state.expanded
                }
            });
        }
        e.stopPropagation();
    }

    protected getRowClickedUpdateObject() {
        return {
            expanded: {
                $set: true
            },
            editing: {
                $set: true
            }
        };
    }

    render() {
        return (
            <div className="alpha grid_15 omega"
                 onClick={() => this.rowClicked()}
                 onKeyDown={e => this.keyPressed(e)}>
                <div className="alpha grid_15 omega"
                     onClick={() => this.rowClicked()}
                     onKeyDown={e => this.keyPressed(e)}>
                    <Field className="alpha grid_3"
                           editing={this.state.editing}
                           field={this.state.field.date}
                           onChange={f => this.onDateChange(f)}
                           autofocus/>
                    <div className="grid_4" onClick={e => this.complexClicked(e)}>
                        &lt;Complex&gt;
                    </div>
                    <Field className="grid_6"
                           editing={this.state.editing}
                           field={this.state.field.comment}
                           onChange={f => this.onCommentChange(f)}/>
                    <Field className="grid_2 omega currency"
                           editing={this.state.editing}
                           field={this.state.field.amount}
                           onChange={f => this.onAmountChange(f)}/>
                </div>
                {this.itemsOrEmptyList()}
            </div>
        );
    }

    private itemsOrEmptyList(): JSX.Element[] {
        if (!this.state.expanded) {
            return [];
        }

        return this.state.field.items.map(item => {
            return <TransactionItemComp field={item}
                                        key={item.id}
                                        editing={this.state.editing}
                                        onTransactionItemChange={f => this.onItemChange(f)}
            />;
        });
    }

    private onItemChange(newItem: TransactionItemField) {
        this.updateField(this.state.field.withItem(newItem));
    }
}

const stateToProps = (state: {}, props: TransactionCompProps) => {
    return {};
};

export const ComplexTransactionComp = connect(stateToProps)(ComplexTransactionCompImpl);
