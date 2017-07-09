import * as React from 'react';
import {ComplexTransaction} from '../../core/transaction';
import {TransactionDate} from './date';
import {Comment} from './comment';
import {Amount} from './amount';
import {TransactionItemComp, TransactionItemField} from './transactionitem';
import {TransactionRowComponent, TransactionRowState} from './row';

class TransactionCompProps {
    transaction: ComplexTransaction;
}

class ComplexTransactionState extends TransactionRowState {
    expanded: boolean = false;
    items: TransactionItemField[]
}

export class ComplexTransactionComp extends TransactionRowComponent<TransactionCompProps, ComplexTransactionState> {
    constructor(props: TransactionCompProps) {
        super();
        let state = new ComplexTransactionState();
        state.items = props.transaction.otherItems.map(item => {
            let itemField = new TransactionItemField();
            itemField.id = item.id;
            return itemField;
        });

        this.state = state;
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


    onItemChange(id: number, newItem: any) {
        let updatedItems = this.state.items.map(item =>
                item.id === id? newItem : item
        );

        this.updateState({
            items: {
                $set: updatedItems
            }
        });
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

    saveChanges(): void {
        console.log("Saving changes")
    }

    render() {
        let {date, amount, comment} = this.props.transaction;
        return (
            <div className="alpha grid_15 omega"
                 onClick={() => this.rowClicked()}
                 onKeyDown={e => this.keyPressed(e)}>
                <TransactionDate date={date} prefix={false} editing={this.state.editing}
                                 autofocus
                                 field={this.state.date}
                                 onChange={f => this.onDateChange(f)}/>
                <div className="grid_4" onClick={e => this.complexClicked(e)}>
                    &lt;Complex&gt;
                </div>
                <Comment comment={comment} short={false} editing={this.state.editing}
                         field={this.state.comment}
                         onChange={f => this.onCommentChange(f)}/>
                <Amount amount={amount} suffix={false} editing={this.state.editing}
                        field={this.state.amount}
                        onChange={f => this.onAmountChange(f)}/>
                {this.itemsOrEmptyList()}
            </div>
        );
    }

    private itemsOrEmptyList(): JSX.Element[] {
        if (!this.state.expanded) {
            return [];
        }

        return this.props.transaction.otherItems.map(item => {
            return <TransactionItemComp item={item}
                                        editing={this.state.editing}
                                        onTransactionItemChange={f => this.onItemChange(item.id, f)}
            />;
        });
    }
}