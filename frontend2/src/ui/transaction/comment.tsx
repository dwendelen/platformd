import * as React from 'react';
import {Field} from './field';
import {ChangeEvent} from 'react';

interface CommentProps {
    comment: string;
    editing: boolean;
    short: boolean;
    onChange: (newValue: Field<string>) => void
    field: Field<string>
}

export class Comment extends React.Component<CommentProps> {
    onChange(e: ChangeEvent<HTMLInputElement>) {
        let newValue = e.target.value;
        this.props.onChange({
            newValue: newValue,
            error: false
        });
    }

    render() {
        let className = this.props.short ? 'grid_4' : 'grid_6';
        if (this.props.editing) {
            return (
                <div className={className}>
                    <input type="text" defaultValue={this.props.comment} onChange={e => this.onChange(e)}/>
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