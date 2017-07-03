import * as React from 'react';
import {Summary} from '../core/summary';

export class SummaryComp extends React.Component<SummaryProps, {}> {
    render() {
        let balance = this.props.summary.balance;
        let name = this.props.summary.name;

        let className = balance < 0 ?
            'grid_2 omega currency negative' : 'grid_2 omega currency';

        return (
            <div className="alpha grid_6 omega">
                <div className="alpha grid_4 name">{name}</div>
                <div className={className}>{balance}</div>
            </div>
        );
    }
}
export class SummaryProps {
    summary: Summary;
}