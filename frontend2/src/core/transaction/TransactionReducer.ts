import {Reducer} from 'redux';
import {SimpleTransaction, Transaction} from './transaction';
import {isUndefined} from 'util';

export const TransactionReducer: Reducer<Transaction[]> =
    (state: Transaction[], action: any): Transaction[] => {
        if(isUndefined(state)) {
            return [];
        }

        switch (action.type) {
            case 'UPDATE_SIMPLE_TRANSACTION':
                return state.map(transaction =>{
                    if(action.uuid === transaction.uuid) {
                        return applyChangesToSimpleTransaction(transaction as SimpleTransaction, action.changes)
                    } else {
                        return transaction;
                    }
                });
            default:
                return state;
        }
    };

function applyChangesToSimpleTransaction(transaction: SimpleTransaction, changes: any[]): SimpleTransaction {
    return changes.reduce(applyChangeToSimpleTransaction, transaction)
}

function applyChangeToSimpleTransaction(transaction: SimpleTransaction, change: any): SimpleTransaction {
    switch (change.type) {
        case 'UPDATE_DATE':
            return {
                ...transaction,
                date: change.newDate
            };
        case 'UPDATE_AMOUNT':
            return {
                ...transaction,
                amount: change.newAmount
            };
        case 'UPDATE_COMMENT':
            return {
                ...transaction,
                comment: change.newComment
            };
        default:
            return transaction
    }
}

