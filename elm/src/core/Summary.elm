module Summary exposing (Summary, accounts)

import List

type alias Summary =
    { uuid: String
    , name: String
    , balance: Float
    }

accounts: List Summary
accounts = [
    { uuid= "a1"
    , name= "Account 1"
    , balance= 23456.79
    },
    { uuid= "a2"
    , name= "Account 2"
    , balance= 123.45
    },
    { uuid= "a3"
    , name= "Negative"
    , balance= -123.45
    },
    { uuid= "a4"
    , name= "Account with a very long name"
    , balance= 23456.79
    },
    { uuid= "a5"
    , name= "Account with a very long name"
    , balance= 1.23
    }]