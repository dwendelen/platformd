import * as React from 'react';
import {AmountField, CommentField, DateField, Field as FieldField} from './fields';
import {ChangeEvent} from 'react';
import {RowChange} from '../../core/transaction/actions';

interface FieldProps<F extends FieldField<F, {}, RowChange>> {
    editing: boolean;
    onChange: (newValue: F) => void;
    field: F;
    autofocus?: boolean;
    className: string;
}

export class Field<F extends FieldField<F, {}, RowChange>> extends React.Component<FieldProps<F>> {
    onChange(e: ChangeEvent<HTMLInputElement>) {
        this.props.onChange(this.props.field.onChange(e.target.value));
    }

    render() {
        let autofocus = this.props.autofocus || false;
        if (this.props.editing) {
            let inputClass = this.props.field.error ? 'error' : '';
            return (
                <div className={this.props.className}>
                    <input
                        autoFocus={autofocus}
                        type="text"
                        className={inputClass}
                        onChange={e => this.onChange(e)}
                        defaultValue={this.props.field.getOldValueAsString()}
                    />
                </div>
            );
        } else {
            return (
                <div className={this.props.className}>
                    {this.props.field.getOldValueAsString() || <div>&nbsp;</div>}
                </div>
            );
        }
    }
}

export class DateFieldComp extends Field<DateField> {
}
export class CommentFieldComp extends Field<CommentField> {
}
export class AmountFieldComp extends Field<AmountField> {
}
