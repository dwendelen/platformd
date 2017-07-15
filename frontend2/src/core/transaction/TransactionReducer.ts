import {Reducer} from 'redux';
import {ComplexTransaction, SimpleTransaction, Transaction, TransactionItem} from './transaction';
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
                        return applyChangesToSimpleTransaction(transaction as SimpleTransaction, action.changes);
                    } else {
                        return transaction;
                    }
                });
            case 'UPDATE_COMPLEX_TRANSACTION':
                return state.map(transaction => {
                   if(action.uuid === transaction.uuid) {
                       return applyChangestoComplexTransaction(transaction as ComplexTransaction, action.changes);
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

function applyChangestoComplexTransaction(transaction: ComplexTransaction, changes: any[]) {
    return changes.reduce(applyChangeToComplexTransaction, transaction)
}

function applyChangeToComplexTransaction(transaction: ComplexTransaction, change: any) {
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
        case 'UPDATE_TRANSACTION_ITEM':
            return updateTransactionItem(transaction, change);
        default:
            return transaction
    }
}

function updateTransactionItem(transaction: ComplexTransaction, action: any) {
    let updatedItems: TransactionItem[] = transaction.otherItems.map(item =>{
        if(item.id === action.id) {
            return applyChangesToTransactionItem(item, action.changes);
        } else {
            return item;
        }
    });
    return {
        ...transaction,
        otherItems: updatedItems
    }
}

function applyChangesToTransactionItem(item: TransactionItem, changes: any[]) {
    return changes.reduce(applyChangeToTransactionItem, item)
}

function applyChangeToTransactionItem(item: TransactionItem, change: any) {
    switch (change.type) {
        case 'UPDATE_DATE':
            return {
                ...item,
                date: change.newDate
            };
        case 'UPDATE_AMOUNT':
            return {
                ...item,
                amount: change.newAmount
            };
        case 'UPDATE_COMMENT':
            return {
                ...item,
                comment: change.newComment
            };
        default:
            return item
    }
}