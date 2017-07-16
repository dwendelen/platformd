import {Action} from 'redux';

export type Change = TransactionChange | ComplexTransactionChange
export type TransactionChange = UpdateSimpleTransaction | UpdateComplexTransaction

export interface UpdateSimpleTransaction extends Action {
    uuid: string
    changes: RowChange[]
}

export interface UpdateComplexTransaction extends Action {
    uuid: string
    changes: ComplexTransactionChange[]
}

export type ComplexTransactionChange = RowChange | UpdateTransactionItem

export interface UpdateTransactionItem extends Action {
    id: number
    changes: RowChange[]
}

export type RowChange = UpdateDate | UpdateComment | UpdateAmount

export interface UpdateDate extends Action {
    newDate: Date
}

export interface UpdateComment extends Action {
    newComment: string
}

export interface UpdateAmount extends Action {
    newAmount: number
}

