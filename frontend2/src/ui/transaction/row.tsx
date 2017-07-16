import * as React from 'react';
import {AmountField, CommentField, DateField, RowField} from './fields';
import * as update from 'immutability-helper';
import {DispatchProp} from 'react-redux';
import {TransactionChange} from '../../core/transaction/actions';

export interface RowState<T extends RowField<T>> {
    field: T;
}

export abstract class RowComponent<P, S extends RowState<T>, T extends RowField<T>> extends React.Component<P, S> {
    onDateChange(date: DateField): void {
        this.updateField(this.getField().withDate(date));
    }

    onCommentChange(comment: CommentField) {
        this.updateField(this.getField().withComment(comment));
    }

    onAmountChange(amount: AmountField) {
        this.updateField(this.getField().withAmount(amount));
    }

    abstract getField(): T;

    abstract updateField(newField: T): void;
}

export interface TransactionRowState<T extends RowField<T>> extends RowState<T> {
    editing: boolean;
}

export abstract class TransactionRowComponent<P extends DispatchProp<TransactionChange>,
    S extends TransactionRowState<T>,
    T extends RowField<T>>
    extends RowComponent<P, S, T> {

    getField() {
        return this.state.field;
    }

    updateState(stateUpdate: {}) {
        this.setState(oldState =>
            update(oldState, stateUpdate)
        );
    }

    updateField(newField: T) {
        this.updateState({
            field: {
                $set: newField
            }
        });
    }

    rowClicked() {
        if (!this.state.editing) {
            this.updateState(this.getRowClickedUpdateObject());
        }
    }

    keyPressed(e: React.KeyboardEvent<HTMLDivElement>) {
        const ESCAPE = 27;
        const ENTER = 13;

        switch (e.keyCode) {
            case ESCAPE:
                this.stopEditing();
                break;
            case ENTER:
                let saved = this.saveChanges();
                if (saved) {
                    this.stopEditing();
                }
                break;
            default:
        }
    }

    saveChanges(): boolean {
        if (this.state.field.isError()) {
            return false;
        }

        let change = this.state.field.getChange();
        if (change !== null) {
            this.props.dispatch(change);
        }
        return true;
    }

    protected getRowClickedUpdateObject() {
        return {
            editing: {
                $set: true
            }
        };
    }

    private stopEditing() {
        this.updateState({
            editing: {
                $set: false
            }
        });
    }
}