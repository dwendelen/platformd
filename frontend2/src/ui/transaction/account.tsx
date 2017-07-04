import * as React from 'react';

interface AccountProps {
    account: string;
    editing: boolean;
}

export class Account extends React.Component<AccountProps> {
    render() {
        if (this.props.editing) {
            return (
                <div className="grid_4">
                    <input type="text" list="destinations" defaultValue={this.props.account} />
                </div>
            );
        } else {
            return (
                <div className="grid_4">
                    {this.props.account}
                </div>
            );
        }
    }
}