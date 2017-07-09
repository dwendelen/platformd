import {Reducer} from 'redux';
import {isUndefined} from 'util';

export const EchoReducer: Reducer<any> =
    (state: any, action: any): any => {
        if(isUndefined(state)) {
            return null;
        }

        return state;
    };

