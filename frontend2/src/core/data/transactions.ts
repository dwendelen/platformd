import {Transaction} from '../transaction';

export const transactions: Transaction[] = [
    {
        uuid: 's1',
        date: new Date(2017, 4, 20),
        otherAccount: 'Other account',
        comment: 'This is supposed to be a cmmnt',
        amount: 356.15
    },
    {
        uuid: 's2',
        date: new Date(2017, 4, 18),
        otherAccount: 'Account with very',
        comment: 'This is also a comment',
        amount: 23456.78
    },
    {
        uuid: 's3',
        date: new Date(2017, 4, 17),
        otherAccount: 'Account with very',
        comment: '',
        amount: 23456.78
    },
    {
        uuid: 's4',
        date: new Date(2017, 4, 18),
        otherAccount: 'Account with very',
        comment: 'This is also a comment',
        amount: 23456.78
    },
    {
        uuid: 's5',
        date: new Date(2017, 4, 17),
        otherAccount: 'Account with very',
        comment: '',
        amount: 23456.78
    },
    {
        uuid: 's6',
        date: new Date(2017, 4, 18),
        otherAccount: 'Account with very',
        comment: 'This is also a comment',
        amount: 23456.78
    },
    {
        uuid: 's7',
        date: new Date(2017, 4, 17),
        otherAccount: 'Account with very',
        comment: '',
        amount: 23456.78
    },
    {
        uuid: 's8',
        date: new Date(2017, 4, 18),
        otherAccount: 'Account with very',
        comment: 'This is also a comment',
        amount: 23456.78
    },
    {
        uuid: 'c1',
        date: new Date(2017, 4, 17),
        comment: 'Lets test complex',
        amount: 23456.78,
        otherItems: [
            {
                id: 0,
                date: new Date(2017, 4, 17),
                account: 'Account with very',
                comment: '',
                amount: 23456.78
            },
            {
                id: 1,
                date: new Date(2017, 4, 18),
                account: 'Account with very',
                comment: 'This is sub',
                amount: 23456.78
            }
        ]
    },
    {
        uuid: 'c2',
        date: new Date(2017, 4, 17),
        comment: 'Lets test complex',
        amount: 23456.78,
        otherItems: [
            {
                id: 0,
                date: new Date(2017, 4, 17),
                account: 'Account with very',
                comment: '',
                amount: 23456.78
            },
            {
                id: 1,
                date: new Date(2017, 4, 18),
                account: 'Account with very',
                comment: 'This is sub',
                amount: 23456.78
            }
        ]
    },
    {
        uuid: 's9',
        date: new Date(2017, 4, 17),
        otherAccount: 'Account with very',
        comment: '',
        amount: 23456.78
    },
    {
        uuid: 's10',
        date: new Date(2017, 4, 18),
        otherAccount: 'Account with very',
        comment: 'This is also a comment',
        amount: 23456.78
    },
    {
        uuid: 's11',
        date: new Date(2017, 4, 17),
        otherAccount: 'Account with very',
        comment: '',
        amount: 23456.78
    }
];