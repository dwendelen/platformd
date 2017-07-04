export class SimpleTransaction {
    date: Date;
    otherAccount: string;
    comment: string;
    amount: number;
}

export class ComplexTransaction {
    date: Date;
    comment: string;
    amount: number;
    otherItems: TransactionItem[];
}

export class TransactionItem {
    date: Date;
    account: string;
    comment: string;
    amount: number;
}

export type Transaction = ComplexTransaction | SimpleTransaction
