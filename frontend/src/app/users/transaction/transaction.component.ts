import {Component, Input} from "@angular/core";
import {Transaction} from "./transaction";

@Component({
    selector: 'transaction',
    templateUrl: './transaction.component.html'

})
export class TransactionComponent {
    @Input()
    transaction: Transaction
}
