import * as React from 'react';
import * as Moment from 'moment';

interface TransactionDateProps {
    date: Date,
    editing: boolean
    prefix: boolean
}

export class TransactionDate extends React.Component<TransactionDateProps> {
    render() {
        let className = this.props.prefix ? 'alpha prefix_1 grid_3' : 'alpha grid_3';
        if (this.props.editing) {
            return (
                <div className={className}>
                    <input type="text" defaultValue={Moment(this.props.date).format('YYYY-MM-DD')}/>
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