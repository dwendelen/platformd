export class SimpleTransaction {
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
    expanded: boolean;
    otherItems: TransactionItem[];
}

export class TransactionItem {
    date: Date;
    account: string;
    comment: string;
    amount: number;
}

export type Transaction = ComplexTransaction | SimpleTransaction
