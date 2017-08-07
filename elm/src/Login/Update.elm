module Login.Update exposing (update)

import Login.Types exposing (..)
import Navigation exposing (newUrl)

update: Msg -> LoginState -> (LoginState, Cmd msg)
update msg state =
    case msg of
        Login data -> (LoggedIn data, newUrl "#/budget")
        Logout -> (LoggedOut, newUrl "#/login")