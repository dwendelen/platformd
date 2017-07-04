import * as React from 'react';

interface CommentProps {
    comment: string;
    editing: boolean;
    short: boolean;
}

export class Comment extends React.Component<CommentProps> {
    render() {
        let className = this.props.short ? 'grid_4' : 'grid_6';
        if (this.props.editing) {
            return (
                <div className={className}>
                    <input type="text" defaultValue={this.props.comment}/>
                </div>
            );
        } else {
            return (
                <div className={className}>
                    {this.props.comment || <div>&nbsp;</div>}
                </div>
            );
        }
    }
}