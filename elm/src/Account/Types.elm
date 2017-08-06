module Account.Types exposing (..)

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
    , expanded: Bool
    }

type alias TransactionItem =
    { id: Float
    , date: String
    , account: String
    , comment: String
    , amount: Float
    }

type Msg = ExpandComplex String
         | CollapseComplex String