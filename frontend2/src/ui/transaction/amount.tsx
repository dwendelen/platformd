import * as React from 'react';
import {AmountField} from './field';
import {ChangeEvent} from 'react';

interface AmountProps {
    editing: boolean;
    suffix: boolean;
    field: AmountField;
    onChange: (newValue: AmountField) => void
}

export class Amount extends React.Component<AmountProps> {
    onChange(e: ChangeEvent<HTMLInputElement>) {
        this.props.onChange(this.props.field.onChange(e.target.value))
    }

    render() {
        let className = this.props.suffix ? 'grid_2 suffix_1 omega currency' : 'grid_2 omega currency';
        if (this.props.editing) {
            let inputClass = this.props.field.error? "currency error": "currency";
            return (
                <div className={className}>
                    <input className={inputClass} type="text" defaultValue={String(this.props.field.oldValue)} onChange={e => this.onChange(e)}/>
                </div>
            );
        } else {
            return (
                <div className={className}>
                    {this.props.field.oldValue}
                </div>
            );
        }
    }
}