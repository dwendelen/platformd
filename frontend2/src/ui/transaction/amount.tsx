import * as React from 'react';
import {Field} from './field';
import {ChangeEvent} from 'react';

interface AmountProps {
    amount: number;
    editing: boolean;
    suffix: boolean;
    field: Field<number>;
    onChange: (newValue: Field<number>) => void
}

export class Amount extends React.Component<AmountProps> {
    onChange(e: ChangeEvent<HTMLInputElement>) {
        let newNumber = Number(e.target.value);

        if(isNaN(newNumber)) {
            this.props.onChange({
                newValue: null,
                error: true
            })
        } else {
            this.props.onChange({
                newValue: newNumber,
                error: false
            })
        }
    }
    render() {
        let className = this.props.suffix ? 'grid_2 suffix_1 omega currency' : 'grid_2 omega currency';
        if (this.props.editing) {
            let inputClass = this.props.field.error? "currency error": "currency";
            return (
                <div className={className}>
                    <input className={inputClass} type="text" defaultValue={String(this.props.amount)} onChange={e => this.onChange(e)}/>
                </div>
            );
        } else {
            return (
                <div className={className}>
                    {this.props.amount}
                </div>
            );
        }
    }
}