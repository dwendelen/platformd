import {Transaction} from '../transaction';

export const transactions: Transaction[] = [
    {
        date: new Date(2017, 4, 20),
        otherAccount: 'Other account',
        comment: 'This is supposed to be a cmmnt',
        amount: 356.15
    },
    {
        date: new Date(2017, 4, 18),
        otherAccount: 'Account with very',
        comment: 'This is also a comment',
        amount: 123456.78
    },
    {
        date: new Date(2017, 4, 17),
        otherAccount: 'Account with very',
        comment: '',
        amount: 123456.78
    },
    {
        date: new Date(2017, 4, 18),
        otherAccount: 'Account with very',
        comment: 'This is also a comment',
        amount: 123456.78
    },
    {
        date: new Date(2017, 4, 17),
        otherAccount: 'Account with very',
        comment: '',
        amount: 123456.78
    },
    {
        date: new Date(2017, 4, 18),
        otherAccount: 'Account with very',
        comment: 'This is also a comment',
        amount: 123456.78
    },
    {
        date: new Date(2017, 4, 17),
        otherAccount: 'Account with very',
        comment: '',
        amount: 123456.78
    },
    {
        date: new Date(2017, 4, 18),
        otherAccount: 'Account with very',
        comment: 'This is also a comment',
        amount: 123456.78
    },
    {
        uuid: 'uuid2',
        date: new Date(2017, 4, 17),
        comment: 'Lets test complex',
        amount: 123456.78,
        expanded: true,
        otherItems: [
            {
                date: new Date(2017, 4, 17),
                account: 'Account with very',
                comment: '',
                amount: 123456.78
            },
            {
                date: new Date(2017, 4, 18),
                account: 'Account with very',
                comment: 'This is sub',
                amount: 123456.78
            }
        ]
    },
    {
        uuid: 'uuid1',
        date: new Date(2017, 4, 17),
        comment: 'Lets test complex',
        amount: 123456.78,
        expanded: true,
        otherItems: [
            {
                date: new Date(2017, 4, 17),
                account: 'Account with very',
                comment: '',
                amount: 123456.78
            },
            {
                date: new Date(2017, 4, 18),
                account: 'Account with very',
                comment: 'This is sub',
                amount: 123456.78
            }
        ]
    },
    {
        date: new Date(2017, 4, 17),
        otherAccount: 'Account with very',
        comment: '',
        amount: 123456.78
    },
    {
        date: new Date(2017, 4, 18),
        otherAccount: 'Account with very',
        comment: 'This is also a comment',
        amount: 123456.78
    },
    {
        date: new Date(2017, 4, 17),
        otherAccount: 'Account with very',
        comment: '',
        amount: 123456.78
    }
];