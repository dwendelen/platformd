module Types exposing (..)

import Account.Types exposing (Transaction)
import Login.Types exposing (LoginState)
import Summary.Types exposing (Summary)

type alias State =
    { route: Route
    , loginState: LoginState
    , summaries: List Summary
    , accountDetails: List Transaction
    }

type Route = LoginPage
           | BudgetPage
           | AccountPage String

type Msg
    = UpdateRoute Route
    | LoginMsg Login.Types.Msg
    | AccountMsg Account.Types.Msg
