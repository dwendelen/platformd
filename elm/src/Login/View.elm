module Login.View exposing (loginPage, logoutComponent)

import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (..)
import Login.Types exposing (Msg(Login, Logout))
import Types exposing (..)

loginPage: State -> Html Types.Msg
loginPage state =
    div [onClick (LoginMsg (Login {token = "token", userId = "userId"}))] [text "Login"]

logoutComponent: Html Types.Msg
logoutComponent = div [class "alpha grid_6 omega", onClick (LoginMsg Logout) ] [text "Logout"]

