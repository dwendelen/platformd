export class SimpleTransaction {
    uuid: string;
    date: Date;
    otherAccount: string;
    comment: string;
    amount: number;
}

export class ComplexTransaction {
    uuid: string;
    date: Date;
    comment: string;
    amount: number;
    otherItems: TransactionItem[];
}

export class TransactionItem {
    id: number;
    date: Date;
    account: string;
    comment: string;
    amount: number;
}

export type Transaction = ComplexTransaction | SimpleTransaction
