import * as React from 'react';
import {DispatchProp} from 'react-redux';
import {RouteComponentProps} from 'react-router';

export class Budget extends React.Component<DispatchProp<{}> & RouteComponentProps<{}>> {
    render() {
        return (
            <div>
                <div className="alpha grid_17 omega center">
                    <h1 className="no-top-margin">Budget</h1>
                </div>

                <div className="alpha grid_16 suffix_1 omega transactions">
                    <div className="alpha grid_4">
                        Bucket
                    </div>
                    <div className="grid_2 currency">
                        Balance
                    </div>
                    <div className="grid_2 currency">
                        Flow
                    </div>
                    <div className="grid_2 currency">
                        Fixed
                    </div>
                    <div className="grid_2 omega currency">
                        Weight
                    </div>
                </div>
                <div className="alpha grid_16 suffix_1 omega transactions">
                    <div className="alpha grid_4">
                        Bucket 2
                    </div>
                    <div className="grid_2 currency">
                        12031.15
                    </div>
                    <div className="grid_2 currency">
                        1170.00
                    </div>
                    <div className="grid_2 currency">
                        60.00
                    </div>
                    <div className="grid_2 omega currency">
                        121
                    </div>
                </div>
            </div>
        );
    }
}