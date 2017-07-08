/*export interface Transaction {

}

export class SimpleTransaction implements Transaction {
    uuid: string;
    date: Date;
    account: string;
    comment: string;
    amount: number;
}

export class ComplexTransaction implements Transaction {
    uuid: string;
    items: TransactionItem[];
}
export class TransactionItem {
    id: number;
    date: Date;
    account: string;
    comment: string;
    amount: number;
}*/

export class Transaction {
    date: Date;
    account: string;
    comment: string;
    amount: number;
}
