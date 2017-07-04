import * as React from 'react';

interface AmountProps {
    amount: number;
    editing: boolean;
    suffix: boolean;
}

export class Amount extends React.Component<AmountProps> {
    render() {
        let className = this.props.suffix ? 'grid_2 suffix_1 omega currency' : 'grid_2 omega currency';
        if (this.props.editing) {
            return (
                <div className={className}>
                    <input className="currency" type="text" defaultValue={String(this.props.amount)}/>
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