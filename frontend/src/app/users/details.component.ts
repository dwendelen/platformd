import {Component} from "@angular/core";
import {Transaction} from "./transaction/transaction";

@Component({
    templateUrl: './details.component.html'
})
export class DetailsComponent {
    transactions: Transaction[] = [
        {
            items: [{
                date: new Date(2017, 4, 20),
                account: 'Other account',
                comment: 'This is supposed to be a cmmnt',
                amount: 356.15
            }]
        },

        {
            items: [{
                date: new Date(2017, 4, 18),
                account: 'Account with very',
                comment: 'This is also a comment',
                amount: 123456.78
            }]
        },
        {
            items: [{
                date: new Date(2017, 4, 17),
                account: 'Account with very',
                comment: '',
                amount: 123456.78
            }]
        },
        {
            items: [{
                date: new Date(2017, 4, 18),
                account: 'Account with very',
                comment: 'This is also a comment',
                amount: 123456.78
            }]
        },
        {
            items: [{
                date: new Date(2017, 4, 17),
                account: 'Account with very',
                comment: '',
                amount: 123456.78
            }]
        },
        {
            items: [{
                date: new Date(2017, 4, 18),
                account: 'Account with very',
                comment: 'This is also a comment',
                amount: 123456.78
            }]
        },
        {
            items: [{
                date: new Date(2017, 4, 17),
                account: 'Account with very',
                comment: '',
                amount: 123456.78
            }]
        },
        {
            items: [{
                date: new Date(2017, 4, 18),
                account: 'Account with very',
                comment: 'This is also a comment',
                amount: 123456.78
            }]
        },
        {
            items: [{
                date: new Date(2017, 4, 17),
                account: 'Account with very',
                comment: '',
                amount: 123456.78
            }]
        },
        {
            items: [
                {
                    date: new Date(2017, 4, 18),
                    account: 'Account with very',
                    comment: 'This is also a comment',
                    amount: 123456.78
                },
                {
                    date: new Date(2017, 4, 17),
                    account: 'Account with very',
                    comment: '',
                    amount: 123456.78
                }
            ]
        },
        {
            items: [{
                date: new Date(2017, 4, 18),
                account: 'Account with very',
                comment: 'This is also a comment',
                amount: 123456.78
            }]
        },
        {
            items: [{
                date: new Date(2017, 4, 17),
                account: 'Account with very',
                comment: '',
                amount: 123456.78
            }]
        }
    ];

}
