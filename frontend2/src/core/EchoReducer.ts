import {Reducer} from 'redux';
import {isUndefined} from 'util';

export const EchoReducer: <T>() => Reducer<T> =
    <T>() => (state: T, action: {}): T | null => {
        if (isUndefined(state)) {
            return null;
        }

        return state;
    };
