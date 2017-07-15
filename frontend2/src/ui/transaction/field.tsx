import * as React from 'react';
import {Field as FieldField} from './fields';
import {ChangeEvent} from 'react';

interface FieldProps<T extends FieldField<any, any>> {
    editing: boolean;
    onChange: (newValue: T) => void;
    field: T;
    autofocus?: boolean;
    className: string;
}


export class Field extends React.Component<FieldProps<any>> {
    onChange(e: ChangeEvent<HTMLInputElement>) {
        this.props.onChange(this.props.field.onChange(e.target.value))
    }

    render() {
        let autofocus = this.props.autofocus || false;
        if (this.props.editing) {
            let inputClass = this.props.field.error? "error ": "";
            return (
                <div className={this.props.className}>
                    <input autoFocus={autofocus} type="text" className={inputClass} onChange={e => this.onChange(e)}
                           defaultValue={this.props.field.getOldValueAsString()}/>
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