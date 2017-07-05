import * as React from 'react';
import { SimpleTransaction } from '../../core/transaction';
import {TransactionDate} from './date';
import {Comment} from './comment';
import {Amount} from './amount';
import {Account} from './account';
import {Field} from './field';
import * as update from 'immutability-helper';


interface TransactionCompProps {
    transaction: SimpleTransaction;
}

interface TransactionCompState {
    editing: boolean;
    date: Field<Date>;
    comment: Field<string>;
}

export class SimpleTransactionComp extends React.Component<TransactionCompProps, TransactionCompState> {
    constructor() {
        super();
        this.state = {
            editing: false,
            date: {
                newValue: null,
                error: false
            },
            comment: {
                newValue: null,
                error: false
            }
        };
    }

    rowClicked() {
        if (!this.state.editing) {
            this.updateState({
                editing: {
                    $set: true
                }
            });
        }
    }

    onDateChange(date: Field<Date>): void {
        this.updateState({
            date: {
                $set: date
            }
        });
    }

    onCommentChange(comment: Field<string>) {
        this.updateState({
           comment: {
               $set: comment
           }
        });
    }

    updateState(stateUpdate: any) {
        this.setState(
            update(this.state, stateUpdate)
        );
    }

    render() {
        let {date, otherAccount, amount, comment} = this.props.transaction;
        return (
            <div className="alpha grid_15 omega" onClick={() => this.rowClicked()}>
                <TransactionDate date={date} field={this.state.date} prefix={false} editing={this.state.editing} onChange={f => this.onDateChange(f)} />
                <Account account={otherAccount} editing={this.state.editing}/>
                <Comment comment={comment} short={false} editing={this.state.editing} field={this.state.comment} onChange={f=> this.onCommentChange(f)}/>
                <Amount amount={amount} suffix={false} editing={this.state.editing} />
            </div>
        );
    }
}