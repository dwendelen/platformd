import * as React from 'react';
import {Summary} from '../core/summary';
import {Link} from 'react-router-dom';

export class SummaryComp extends React.Component<SummaryProps, {}> {
    render() {
        let {balance, name, uuid} = this.props.summary;

        let className = balance < 0 ?
            'grid_2 omega currency negative' : 'grid_2 omega currency';

        return (<Link to={`/accounts/${uuid}`}>
            <div className="alpha grid_6 omega">

                    <div className="alpha grid_4 name">{name}</div>
                    <div className={className}>{balance}</div>

            </div></Link>
        );
    }
}
export class SummaryProps {
    summary: Summary;
}