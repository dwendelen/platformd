import * as React from 'react';
import {ComplexTransaction} from '../../core/transaction';
import {TransactionDate} from './date';
import {Comment} from './comment';
import {Amount} from './amount';
import {Field} from './field';
import * as update from 'immutability-helper';
import {TransactionItemComp, TransactionItemField} from './transactionitem';

interface TransactionCompProps {
    transaction: ComplexTransaction;
}

interface TransactionCompState {
    editing: boolean;
    expanded: boolean;
    date: Field<Date>;
    comment: Field<string>;
    items: TransactionItemField[]
}

export class ComplexTransactionComp extends React.Component<TransactionCompProps, TransactionCompState> {
    constructor(props: TransactionCompProps) {
        super();
        let items = props.transaction.otherItems.map(item => ({
            id: item.id,
            date: {
                newValue: null,
                error: false
            },
            comment: {
                newValue: null,
                error: false
            }
        }));

        this.state = {
            editing: false,
            expanded: false,
            date: {
                newValue: null,
                error: false
            },
            comment: {
                newValue: null,
                error: false
            },
            items: items
        };
    }

    rowClicked() {
        if (!this.state.editing) {
            this.updateState({
                expanded: {
                    $set: true
                },
                editing: {
                    $set: true
                }
            });
        }
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

    onDateChange(date: Field<Date>): void {
        this.updateState( {
            date: {
                $set: date
            }
        });
    }

    onItemDateChange(id: number, date: Field<Date>) {
        this.updateItem(id, {
            date: {
                $set: date
            }
        });
    }

    onCommentChange(comment: Field<string>): void {
            this.updateState( {
                comment: {
                    $set: comment
                }
            });
        }

    onItemCommentChange(id: number, comment: Field<string>) {
        this.updateItem(id, {
            comment: {
                $set: comment
            }
        });
    }

    updateItem(id: number, itemUpdate: any) {
        let updatedItems = this.state.items.map(item =>
                item.id === id? update(item, itemUpdate) : item
        );

        this.updateState({
            items: {
                $set: updatedItems
            }
        });
    }

    updateState(stateUpdate: any) {
        this.setState(
            update(this.state, stateUpdate)
        );
    }

    render() {
        let {date, amount, comment} = this.props.transaction;
        return (
            <div className="alpha grid_15 omega" onClick={() => this.rowClicked()}>
                <TransactionDate date={date} prefix={false} editing={this.state.editing} field={this.state.date}
                                 onChange={f => this.onDateChange(f)}/>
                <div className="grid_4" onClick={e => this.complexClicked(e)}>
                    &lt;Complex&gt;
                </div>
                <Comment comment={comment} short={false} editing={this.state.editing} field={this.state.comment}
                         onChange={f => this.onCommentChange(f)}/>
                <Amount amount={amount} suffix={false} editing={this.state.editing}/>
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
                                        field={this.getItemField(item.id)}
                                        onDateChange={f => this.onItemDateChange(item.id, f)}
                                        onCommentChange={f => this.onItemCommentChange(item.id, f)}
            />;
        });
    }

    private getItemField(id: number): TransactionItemField {
        return this.state.items
            .filter(i => i.id === id)[0];
    }
}