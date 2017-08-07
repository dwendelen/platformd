module Login.View exposing (loginPage, logoutComponent)

import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (..)
import Login.Types exposing (..)

loginPage: LoginState -> Html Msg
loginPage state =
    div [onClick (Login {token = "token", userId = "userId"})] [text "Login"]

logoutComponent: Html Msg
logoutComponent = div [class "alpha grid_6 omega", onClick Logout ] [text "Logout"]