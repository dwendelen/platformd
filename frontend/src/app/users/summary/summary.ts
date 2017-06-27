import {Observable} from "rxjs/Observable";

export class Summary<Type> {
    name: string;
    balance: number;
    reference: Observable<Type>
}
