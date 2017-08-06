module Transaction exposing (Transaction, transactions)

import Date exposing (Date)

type Transaction =
      ComplexTransaction ComplexTransactionData
    | SimpleTransaction SimpleTransactionData

type alias SimpleTransactionData =
    { uuid: String
    , date: String
    , otherAccount: String
    , comment: String
    , amount: Float
    }

type alias ComplexTransactionData =
    { uuid: String
    , date: String
    , comment: String
    , amount: Float
    , otherItems: List TransactionItem
    }

type alias TransactionItem =
    { id: Float
    , date: String
    , account: String
    , comment: String
    , amount: Float
    }


transactions: List Transaction
transactions = [
    SimpleTransaction { uuid= "s1"
    , date= "2017-04-20"
    , otherAccount= "Other account"
    , comment= "This is supposed to be a cmmnt"
    , amount= 356.15
    },
    SimpleTransaction { uuid= "s2"
    , date= "2017-04-08"
    , otherAccount= "Account with very"
    , comment= "This is also a comment"
    , amount= 23456.78
    },
    SimpleTransaction { uuid= "s3"
    , date= "2017-04-17"
    , otherAccount= "Account with very"
    , comment= ""
    , amount= 23456.78
    },
    SimpleTransaction { uuid= "s4"
    , date= "2017-04-08"
    , otherAccount= "Account with very"
    , comment= "This is also a comment"
    , amount= 23456.78
    },
    SimpleTransaction { uuid= "s5"
    , date= "2017-04-07"
    , otherAccount= "Account with very"
    , comment= ""
    , amount= 23456.78
    },
    SimpleTransaction { uuid= "s6"
    , date= "2017-04-08"
    , otherAccount= "Account with very"
    , comment= "This is also a comment"
    , amount= 23456.78
    },
    SimpleTransaction { uuid= "s7"
    , date= "2017-04-07"
    , otherAccount= "Account with very"
    , comment= ""
    , amount= 23456.78
    },
    SimpleTransaction { uuid= "s8"
    , date= "2017-04-08"
    , otherAccount= "Account with very"
    , comment= "This is also a comment"
    , amount= 23456.78
    },
    ComplexTransaction { uuid= "c1"
    , date= "2017-04-07"
    , comment= "Lets test complex"
    , amount= 23456.78
    , otherItems= [
        { id= 0
        , date= "2017-04-07"
        , account= "Account with very"
        , comment= ""
        , amount= 23456.78
        },
        { id= 1
        , date= "2017-04-08"
        , account= "Account with very"
        , comment= "This is sub"
        , amount= 23456.78
        }]
    },
    ComplexTransaction { uuid= "c2"
    , date= "2017-04-07"
    , comment= "Lets test complex"
    , amount= 23456.78
    , otherItems= [
        { id= 0
        , date= "2017-04-07"
        , account= "Account with very"
        , comment= ""
        , amount= 23456.78
        },
        { id= 1
        , date= "2017-04-08"
        , account= "Account with very"
        , comment= "This is sub"
        , amount= 23456.78
        }]
    },
    SimpleTransaction { uuid= "s9"
    , date= "2017-04-07"
    , otherAccount= "Account with very"
    , comment= ""
    , amount= 23456.78
    },
    SimpleTransaction { uuid= "s10"
    , date= "2017-04-08"
    , otherAccount= "Account with very"
    , comment= "This is also a comment"
    , amount= 23456.78
    },
    SimpleTransaction { uuid= "s11"
    , date= "2017-04-07"
    , otherAccount= "Account with very"
    , comment= ""
    , amount= 23456.78
    }]