import {Reducer} from 'redux';
import {ComplexTransaction, SimpleTransaction, Transaction, TransactionItem} from './transaction';
import {isUndefined} from 'util';
import {
    ComplexTransactionChange,
    RowChange, TransactionChange, UpdateAmount, UpdateComment, UpdateComplexTransaction, UpdateDate,
    UpdateSimpleTransaction, UpdateTransactionItem
} from './actions';

export const TransactionReducer: Reducer<Transaction[]> =
    (state: Transaction[], action: TransactionChange): Transaction[] => {
        if (isUndefined(state)) {
            return [];
        }

        switch (action.type) {
            case 'UPDATE_SIMPLE_TRANSACTION':
                let updateSimpleTransaction = action as UpdateSimpleTransaction;
                return state.map(transaction => {
                    if (action.uuid === transaction.uuid) {
                        return applyChangesToSimpleTransaction(
                            transaction as SimpleTransaction,
                            updateSimpleTransaction.changes
                        );
                    } else {
                        return transaction;
                    }
                });
            case 'UPDATE_COMPLEX_TRANSACTION':
                return state.map(transaction => {
                    let updateComplexTransaction = action as UpdateComplexTransaction;

                    if (action.uuid === transaction.uuid) {
                        return applyChangestoComplexTransaction(
                            transaction as ComplexTransaction,
                            updateComplexTransaction.changes
                        );
                    } else {
                        return transaction;
                    }
                });
            default:
                return state;
        }
    };

function applyChangesToSimpleTransaction(transaction: SimpleTransaction, changes: RowChange[]): SimpleTransaction {
    return changes.reduce(applyChangeToSimpleTransaction, transaction);
}

function applyChangeToSimpleTransaction(transaction: SimpleTransaction, change: RowChange): SimpleTransaction {
    switch (change.type) {
        case 'UPDATE_DATE':
            let updateDate = change as UpdateDate;
            return {
                ...transaction,
                date: updateDate.newDate
            };
        case 'UPDATE_AMOUNT':
            let updateAmount = change as UpdateAmount;
            return {
                ...transaction,
                amount: updateAmount.newAmount
            };
        case 'UPDATE_COMMENT':
            let updateComment = change as UpdateComment;
            return {
                ...transaction,
                comment: updateComment.newComment
            };
        default:
            return transaction;
    }
}

function applyChangestoComplexTransaction(transaction: ComplexTransaction, changes: ComplexTransactionChange[]) {
    return changes.reduce(applyChangeToComplexTransaction, transaction);
}

function applyChangeToComplexTransaction(transaction: ComplexTransaction, change: ComplexTransactionChange) {
    switch (change.type) {
        case 'UPDATE_DATE':
            let updateDate = change as UpdateDate;
            return {
                ...transaction,
                date: updateDate.newDate
            };
        case 'UPDATE_AMOUNT':
            let updateAmount = change as UpdateAmount;
            return {
                ...transaction,
                amount: updateAmount.newAmount
            };
        case 'UPDATE_COMMENT':
            let updateComment = change as UpdateComment;
            return {
                ...transaction,
                comment: updateComment.newComment
            };
        case 'UPDATE_TRANSACTION_ITEM':
            return updateTransactionItem(transaction, change as UpdateTransactionItem);
        default:
            return transaction;
    }
}

function updateTransactionItem(transaction: ComplexTransaction, action: UpdateTransactionItem) {
    let updatedItems: TransactionItem[] = transaction.otherItems.map(item => {
        if (item.id === action.id) {
            return applyChangesToTransactionItem(item, action.changes);
        } else {
            return item;
        }
    });
    return {
        ...transaction,
        otherItems: updatedItems
    };
}

function applyChangesToTransactionItem(item: TransactionItem, changes: RowChange[]) {
    return changes.reduce(applyChangeToTransactionItem, item);
}

function applyChangeToTransactionItem(item: TransactionItem, change: RowChange) {
    switch (change.type) {
        case 'UPDATE_DATE':
            let updateDate = change as UpdateDate;
            return {
                ...item,
                date: updateDate.newDate
            };
        case 'UPDATE_AMOUNT':
            let updateAmount = change as UpdateAmount;
            return {
                ...item,
                amount: updateAmount.newAmount
            };
        case 'UPDATE_COMMENT':
            let updateComment = change as UpdateComment;
            return {
                ...item,
                comment: updateComment.newComment
            };
        default:
            return item;
    }
}