import * as Moment from 'moment';
import {
    Change, ComplexTransactionChange, RowChange, TransactionChange, UpdateAmount, UpdateComment,
    UpdateComplexTransaction,
    UpdateDate, UpdateSimpleTransaction, UpdateTransactionItem
} from '../../core/transaction/actions';

export interface FieldLike<C extends Change> {
    isError(): boolean;
    getChange(): C | null;
}

export interface Field<F extends Field<F, T, C>, T, C extends RowChange> extends FieldLike<C> {
    oldValue: T;
    error: boolean;
    getOldValueAsString(): string;
    onChange(newValue: string): F;
}

abstract class AbstractField<F extends AbstractField<F, T, C>, T, C extends RowChange> implements Field<F, T, C> {
    oldValue: T;
    newValue: T | null;
    error: boolean;

    constructor(oldValue: T, newValue: T | null = null, error: boolean = false) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.error = error;
    }

    abstract onChange(newValue: string): F;

    abstract generateChange(): C;

    abstract getOldValueAsString(): string;

    isError(): boolean {
        return this.error;
    }

    getChange(): C | null {
        if (this.newValue === null || this.newValue === this.oldValue) {
            return null;
        }

        return this.generateChange();
    }
}

export class DateField extends AbstractField<DateField, Date, UpdateDate> {
    getOldValueAsString(): string {
        return Moment(this.oldValue).format('YYYY-MM-DD');
    }

    onChange(newValue: string): DateField {
        let moment = Moment(newValue, 'YYYY-MM-DD', true);
        if (moment.isValid()) {
            return new DateField(this.oldValue, moment.toDate());
        } else {
            return new DateField(this.oldValue, null, true);
        }
    }

    generateChange(): UpdateDate {
        return {
            type: 'UPDATE_DATE',
            newDate: this.newValue as Date
        };
    }
}

export class AmountField extends AbstractField<AmountField, number, UpdateAmount> {
    getOldValueAsString(): string {
        return String(this.oldValue);
    }

    onChange(newValue: string): AmountField {
        let newNumber = Number(newValue);

        if (isNaN(newNumber)) {
            return new AmountField(this.oldValue, null, true);
        } else {
            return new AmountField(this.oldValue, newNumber);
        }
    }

    generateChange(): UpdateAmount {
        return {
            type: 'UPDATE_AMOUNT',
            newAmount: this.newValue as number
        };
    }
}

export class CommentField extends AbstractField<CommentField, string, UpdateComment> {
    getOldValueAsString(): string {
        return this.oldValue;
    }

    onChange(newValue: string): CommentField {
        return new CommentField(this.oldValue, newValue);
    }

    generateChange(): UpdateComment {
        return {
            type: 'UPDATE_COMMENT',
            newComment: this.newValue as string
        };
    }
}

abstract class CompositeField<C extends Change, S extends Change> implements FieldLike<C> {
    fields: FieldLike<S>[];

    constructor(fields: FieldLike<S>[]) {
        this.fields = fields;
    }

    isError(): boolean {
        let nbOfFieldsWithError = this.fields
            .filter(f => f.isError())
            .length;

        return nbOfFieldsWithError !== 0;
    }

    getChange(): C | null {
        let changes: S[] = [];
        this.fields.forEach(f => {
            const change = f.getChange();
            if (change === null) {
                return;
            }
            changes.push(change);
        });

        if (changes.length === 0) {
            return null;
        }

        return this.generateChange(changes);
    }

    abstract generateChange(changes: S[]): C;
}

type TransOrItem = TransactionChange | UpdateTransactionItem;
export abstract class RowField<T extends RowField<T>> extends CompositeField<TransOrItem, ComplexTransactionChange> {
    date: DateField;
    comment: CommentField;
    amount: AmountField;

    constructor(date: DateField,
                comment: CommentField, amount: AmountField,
                extraFields: TransactionItemField[] = []) {
        const basicFields = [date, comment, amount] as FieldLike<ComplexTransactionChange>[];
        super(basicFields.concat(extraFields as FieldLike<ComplexTransactionChange>[]));

        this.date = date;
        this.comment = comment;
        this.amount = amount;
    }

    abstract withDate(date: DateField): T;

    abstract withComment(comment: CommentField): T;

    abstract withAmount(amount: AmountField): T;
}

export class SimpleTransactionField extends RowField<SimpleTransactionField> {
    uuid: string;

    constructor(uuid: string, date: DateField, comment: CommentField, amount: AmountField) {
        super(date, comment, amount);
        this.uuid = uuid;
    }

    withDate(date: DateField): SimpleTransactionField {
        return new SimpleTransactionField(this.uuid, date, this.comment, this.amount);
    }

    withComment(comment: CommentField): SimpleTransactionField {
        return new SimpleTransactionField(this.uuid, this.date, comment, this.amount);
    }

    withAmount(amount: AmountField): SimpleTransactionField {
        return new SimpleTransactionField(this.uuid, this.date, this.comment, amount);
    }

    generateChange(changes: RowChange[]): UpdateSimpleTransaction {
        return {
            type: 'UPDATE_SIMPLE_TRANSACTION',
            uuid: this.uuid,
            changes: changes
        };
    }
}

export class ComplexTransactionField extends RowField<ComplexTransactionField> {
    uuid: string;
    items: TransactionItemField[];

    constructor(uuid: string,
                date: DateField,
                comment: CommentField,
                amount: AmountField,
                items: TransactionItemField[]) {
        super(date, comment, amount, items);
        this.uuid = uuid;
        this.items = items;
    }

    withDate(date: DateField): ComplexTransactionField {
        return new ComplexTransactionField(this.uuid, date, this.comment, this.amount, this.items);
    }

    withComment(comment: CommentField): ComplexTransactionField {
        return new ComplexTransactionField(this.uuid, this.date, comment, this.amount, this.items);
    }

    withAmount(amount: AmountField): ComplexTransactionField {
        return new ComplexTransactionField(this.uuid, this.date, this.comment, amount, this.items);
    }

    withItem(newItem: TransactionItemField): ComplexTransactionField {
        let updatedItems = this.items.map(item =>
            item.id === newItem.id ? newItem : item
        );

        return new ComplexTransactionField(this.uuid, this.date, this.comment, this.amount, updatedItems);
    }

    generateChange(changes: ComplexTransactionChange[]): UpdateComplexTransaction {
        return {
            type: 'UPDATE_COMPLEX_TRANSACTION',
            uuid: this.uuid,
            changes: changes
        };
    }
}

export class TransactionItemField extends RowField<TransactionItemField> {
    id: number;

    constructor(id: number, date: DateField, comment: CommentField, amount: AmountField) {
        super(date, comment, amount);
        this.id = id;
    }

    withDate(date: DateField): TransactionItemField {
        return new TransactionItemField(this.id, date, this.comment, this.amount);
    }

    withComment(comment: CommentField): TransactionItemField {
        return new TransactionItemField(this.id, this.date, comment, this.amount);
    }

    withAmount(amount: AmountField): TransactionItemField {
        return new TransactionItemField(this.id, this.date, this.comment, amount);
    }

    generateChange(changes: RowChange[]): UpdateTransactionItem {
        return {
            type: 'UPDATE_TRANSACTION_ITEM',
            id: this.id,
            changes: changes
        };
    }
}