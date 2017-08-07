import Account.Types
import Account.Update
import Account.View exposing (accountPage)
import Budget.View exposing (budgetPage)
import Html exposing (..)
import Html.Attributes exposing (..)
import List exposing (map)
import Login.Types exposing (LoginState(LoggedOut), isLoggedIn)
import Login.View exposing (loginPage, logoutComponent)
import Maybe exposing (withDefault)
import Navigation

import Summary.Data exposing (accounts)
import Summary.Types exposing (Summary)
import Summary.View exposing (accountSummary)
import Account.Data exposing (transactions)
import Types exposing (..)
import Update exposing (update)
import UrlParser exposing (parseHash, (</>))


main: Program Never State Msg
main =
    Navigation.program updateUrl
        { init = init
        , view = view
        , update = update
        , subscriptions = subscriptions
        }


updateUrl: Navigation.Location -> Msg
updateUrl location =
    UpdateRoute (route location)

-- Model
init : Navigation.Location -> (State, Cmd Msg)
init location =
    (initialState location, Cmd.none)

initialState:  Navigation.Location -> State
initialState location =
    { route = route location
    , loginState = LoggedOut
    , summaries = accounts
    , accountDetails = transactions
    }


-- View
view: State -> Html Msg
view state =
    div [] [
        stylesheet "styles.css",
        stylesheet "960_24.css",
        div [class "header"] [],
        div [class "container_24"] [
            navigationBar state,
            div [id "content", class "prefix_1 grid_17"] [
                pageRouter state
            ]
        ]
    ]

navigationBar state =
    div [id "nav", class "grid_6"] (
        h1 [class "no-top-margin"] [text "Menu"] ::
        navigationBarContent state
    )

navigationBarContent state =
    if isLoggedIn state.loginState then
        a [href "#/budget"] [
            div [class "alpha grid_6 omega"] [ text "Budget"]
        ] ::
        List.map accountSummary state.summaries ++
        [ Html.map LoginMsg logoutComponent ]
    else
        [
            a [href "#/login"] [
                div [class "alpha grid_6 omega"] [ text "Login"]
            ]
        ]

stylesheet: String -> Html Msg
stylesheet url =
    let
        attrs =
            [ attribute "rel"       "stylesheet"
            , attribute "property"  "stylesheet"
            , attribute "href"      url
            ]
    in
        node "link" attrs []

pageRouter: State -> Html Msg
pageRouter state =
    case state.route of
        LoginPage -> Html.map LoginMsg (loginPage state.loginState)
        BudgetPage -> budgetPage state
        AccountPage account -> accountPage state account


route: Navigation.Location -> Route
route location =
    parseHash routes location
    |> withDefault LoginPage


routes =
    UrlParser.oneOf [
        UrlParser.map BudgetPage (UrlParser.s "budget"),
        UrlParser.map AccountPage (UrlParser.s "accounts" </> UrlParser.string)
    ]


-- Subscriptions
subscriptions: State -> Sub Msg
subscriptions state =
    Sub.none