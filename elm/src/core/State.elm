module State exposing (State)

import Array exposing (Array)
import Summary exposing (Summary)
import Transaction exposing (Transaction)

type alias State =
    { loginState: LoginState
    , summaries: List Summary
    , accountDetails: List Transaction
    }

type alias LoginState =
    { loggedIn: Bool
    , token: String
    , userId: String
    }