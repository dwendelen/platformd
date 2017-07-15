import {Component} from 'react';
import {AmountField, CommentField, DateField, RowField} from './field';
import * as update from 'immutability-helper'
import {DispatchProp} from 'react-redux';

export interface RowState<T extends RowField<T>> {
    field: T;
}

export abstract class RowComponent<P, S extends RowState<T>, T extends RowField<T>> extends Component<P, S> {
    onDateChange(date: DateField): void {
        this.updateField(this.state.field.withDate(date));
    }

    onCommentChange(comment: CommentField) {
        this.updateField(this.state.field.withComment(comment));
    }

    onAmountChange(amount: AmountField) {
        this.updateField(this.state.field.withAmount(amount));
    }

    updateField(newField: T) {
        this.updateState({
            field: {
                $set: newField
            }
        });
        this.onUpdated(newField);
    }

    updateState(stateUpdate: any) {
        let newState = update(this.state, stateUpdate);
        this.setState(
            newState
        );
    }

    onUpdated(newField: T) {}
}

export interface TransactionRowState<T extends RowField<T>> extends RowState<T> {
    editing: boolean;
}

export abstract class TransactionRowComponent<P extends DispatchProp<any>, S extends TransactionRowState<T>, T extends RowField<T>> extends RowComponent<P, S, T> {
    rowClicked() {
        if (!this.state.editing) {
            this.updateState(this.getRowClickedUpdateObject());
        }
    }

    keyPressed(e: React.KeyboardEvent<HTMLDivElement>) {
        const ESCAPE = 27;
        const ENTER = 13;

        switch(e.keyCode) {
            case ESCAPE:
                this.stopEditing();
                break;
            case ENTER:
                let saved = this.saveChanges();
                if (saved) {
                    this.stopEditing();
                }
                break;
        }
    }

    saveChanges(): boolean {
        if(this.state.field.isError()) {
            return false;
        }

        let change = this.state.field.getChange();
        if(change !== null) {
            this.props.dispatch(change);
        }
        return true;
    }

    private stopEditing() {
        this.updateState({
            editing: {
                $set: false
            }
        });
    }

    protected getRowClickedUpdateObject() {
        return {
            editing: {
                $set: true
            }
        };
    }
}