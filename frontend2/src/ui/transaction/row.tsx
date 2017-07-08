import {Component} from 'react';
import {Field} from './field';
import * as update from 'immutability-helper'

export class RowState {
    date: Field<Date> = new Field<Date>();
    //account: Field<Summary>;
    comment: Field<string> = new Field<string>();
    amount: Field<number> = new Field<number>();
    isError(): boolean {
        return  this.date.error ||
            this.comment.error;
    }
}

export abstract class RowComponent<P, S extends RowState> extends Component<P, S> {
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

    onAmountChange(amount: Field<number>) {
        this.updateState({
            amount: {
                $set: amount
            }
        });
    }

    updateState(stateUpdate: any) {
        let newState = update(this.state, stateUpdate);
        this.setState(
            newState
        );
        this.onUpdated(newState)
    }

    onUpdated(newState: S) {}
}