module Types exposing (..)

import Account.Types exposing (Transaction)
import Console.Types exposing (ConsoleState)
import Login.Types exposing (LoginState)
import Summary.Types exposing (Summary)

type alias State =
    { route: Route
    , loginState: LoginState
    , summaries: List Summary
    , accountDetails: List Transaction
    , consoleState: ConsoleState Msg
    }

type Route = LoginPage
           | BudgetPage
           | AccountPage String

type Msg
    = UpdateRoute Route
    | LoginMsg Login.Types.Msg
    | AccountMsg Account.Types.Msg
    | ConsoleMsg Console.Types.Msg
    | ChangeUrl String
