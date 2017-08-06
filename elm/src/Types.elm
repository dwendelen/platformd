module Types exposing (..)

import Account.Types exposing (Transaction)
import Summary.Types exposing (Summary)

type alias UiState =
    { route: Route
    , appState: State
    }

type Route = LoginPage
           | BudgetPage
           | AccountPage String

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

type Msg
    = UpdateRoute Route
    | AccountMsg Account.Types.Msg
