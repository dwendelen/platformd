import {Component, Input} from "@angular/core";
import {Summary} from "./summary";

@Component({
    selector: 'summary',
    template: `
<div class="alpha grid_6 omega">
    <div class="name">{{item.name}}</div>
    <div class="currency" [class.negative]="item.balance < 0">
         {{item.balance | number: '1.2-2'}}
    </div>
</div>
`
})
export class SummaryComponent {
    @Input()
    item: Summary<any>
}
