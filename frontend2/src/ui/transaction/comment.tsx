import * as React from 'react';
import {CommentField} from './field';
import {ChangeEvent} from 'react';

interface CommentProps {
    editing: boolean;
    short: boolean;
    onChange: (newValue: CommentField) => void
    field: CommentField
}

export class Comment extends React.Component<CommentProps> {
    onChange(e: ChangeEvent<HTMLInputElement>) {
        this.props.onChange(this.props.field.onChange(e.target.value))
    }

    render() {
        let className = this.props.short ? 'grid_4' : 'grid_6';
        if (this.props.editing) {
            return (
                <div className={className}>
                    <input type="text" defaultValue={this.props.field.oldValue} onChange={e => this.onChange(e)}/>
                </div>
            );
        } else {
            return (
                <div className={className}>
                    {this.props.field.oldValue || <div>&nbsp;</div>}
                </div>
            );
        }
    }
}