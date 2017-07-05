import * as React from 'react';
import * as Moment from 'moment';
import {ChangeEvent} from 'react'
import { Field} from './field';

interface TransactionDateProps {
    date: Date,
    editing: boolean
    prefix: boolean
    onChange: (newValue: Field<Date>) => void
    field: Field<Date>
}


export class TransactionDate extends React.Component<TransactionDateProps> {
    onChange(e: ChangeEvent<HTMLInputElement>) {
        let newValue = e.target.value;
        let moment = Moment(newValue, 'YYYY-MM-DD', true);
        if(moment.isValid()) {
            this.props.onChange({
                newValue: moment.toDate(),
                error: false
            })
        } else {
            this.props.onChange({
                newValue: null,
                error: true
            })
        }
    }

    render() {
        let className = this.props.prefix ? 'alpha prefix_1 grid_3' : 'alpha grid_3';
        if (this.props.editing) {
            let inputClass = this.props.field.error? "error": "";
            return (
                <div className={className}>
                    <input type="text" className={inputClass} onChange={e => this.onChange(e)}
                           defaultValue={Moment(this.props.date).format('YYYY-MM-DD')}/>
                </div>
            );
        } else {
            return (
                <div className={className}>
                    {Moment(this.props.date).format('YYYY-MM-DD')}
                </div>
            );
        }
    }
}