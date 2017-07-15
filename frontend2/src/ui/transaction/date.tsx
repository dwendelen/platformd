import * as React from 'react';
import * as Moment from 'moment';
import {ChangeEvent} from 'react'
import {DateField} from './field';

class TransactionDateProps {
    editing: boolean;
    prefix: boolean;
    onChange: (newValue: DateField) => void;
    field: DateField;
    autofocus?: boolean;
}


export class TransactionDate extends React.Component<TransactionDateProps> {
    onChange(e: ChangeEvent<HTMLInputElement>) {
        this.props.onChange(this.props.field.onChange(e.target.value))
    }

    render() {
        let autofocus = this.props.autofocus || false;
        let className = this.props.prefix ? 'alpha prefix_1 grid_3' : 'alpha grid_3';
        if (this.props.editing) {
            let inputClass = this.props.field.error? "error": "";
            return (
                <div className={className}>
                    <input autoFocus={autofocus} type="text" className={inputClass} onChange={e => this.onChange(e)}
                           defaultValue={Moment(this.props.field.oldValue).format('YYYY-MM-DD')}/>
                </div>
            );
        } else {
            return (
                <div className={className}>
                    {Moment(this.props.field.oldValue).format('YYYY-MM-DD')}
                </div>
            );
        }
    }
}