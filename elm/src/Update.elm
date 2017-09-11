module Update exposing (update)

import Account.Types
import Account.Update
import Login.Update
import Types exposing (..)
import Console.Update
import Navigation exposing (newUrl)


update : Msg->State->(State, Cmd Msg)
update msg state =
    case msg of
        UpdateRoute newRoute ->
            ({state | route = newRoute}, Cmd.none)
        AccountMsg msg ->
            ({state | accountDetails = (Account.Update.update msg state.accountDetails)}, Cmd.none)
        LoginMsg msg ->
            let
                (newLoginState, cmd) = Login.Update.update msg state.loginState
            in
                ({state | loginState = newLoginState}, cmd)
        ConsoleMsg msg ->
            let
               (newConsoleState, newMsg) = Console.Update.update msg state.consoleState
               newState = {state | consoleState = newConsoleState}
            in
                case newMsg of
                    Just todo -> update todo newState
                    Nothing -> (newState, Cmd.none)
        ChangeUrl url ->
            (state, newUrl url)