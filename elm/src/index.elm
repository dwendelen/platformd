import Account.Types
import Account.Update
import Account.View exposing (accountPage)
import Budget.View exposing (budgetPage)
import Html exposing (..)
import Html.Attributes exposing (..)
import List exposing (map)
import Maybe exposing (withDefault)
import Navigation

import Summary.Data exposing (accounts)
import Summary.Types exposing (Summary)
import Summary.View exposing (accountSummary)
import Account.Data exposing (transactions)
import Types exposing (Msg(AccountMsg, UpdateRoute), Route(AccountPage, BudgetPage, LoginPage), State, UiState)
import UrlParser exposing (parseHash, (</>))

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


main: Program Never UiState Msg
main =
    Navigation.program updateUrl
        { init = init
        , view = view
        , update = update
        , subscriptions = subscriptions
        }

-- Model
init : Navigation.Location -> (UiState, Cmd Msg)
init location =
    (initialState location, Cmd.none)

initialState:  Navigation.Location -> UiState
initialState location =
    { route = route location
    , appState =
        { loginState = {loggedIn = False, token = "", userId = ""}
        , summaries = accounts
        , accountDetails = transactions
        }
    }

-- Update

update : Msg->UiState->(UiState, Cmd Msg)
update msg state =
    case msg of
        UpdateRoute newRoute ->
            ({state | route = newRoute}, Cmd.none)
        AccountMsg msg -> ({state | appState = updateAccountDetails state.appState msg}, Cmd.none)

updateAccountDetails: State -> Account.Types.Msg -> State
updateAccountDetails appState msg =
    {appState | accountDetails = (Account.Update.update msg appState.accountDetails)}

updateUrl: Navigation.Location -> Msg
updateUrl location =
    UpdateRoute (route location)

-- View
view: UiState -> Html Msg
view state =
    div [] [
        stylesheet "styles.css",
        stylesheet "960_24.css",
        div [class "header"] [],
        div [class "container_24"] [
            div [id "nav", class "grid_6"] ([
                h1 [class "no-top-margin"] [text "Menu"],
                a [href "#/budget"] [
                    div [class "alpha grid_6 omega"] [ text "Budget"]
                ]
            ] ++ List.map accountSummary state.appState.summaries ++ [
                logoutComponent
            ]),
            div [id "content", class "prefix_1 grid_17"] [
                pageRouter state
            ]
        ]
    ]

pageRouter: UiState -> Html Msg
pageRouter state =
    case state.route of
        LoginPage -> loginPage state.appState
        BudgetPage -> budgetPage state.appState
        AccountPage account -> accountPage state.appState account


route: Navigation.Location -> Route
route location =
    parseHash routes location
    |> withDefault LoginPage

routes =
    UrlParser.oneOf [
        UrlParser.map BudgetPage (UrlParser.s "budget"),
        UrlParser.map AccountPage (UrlParser.s "accounts" </> UrlParser.string)
    ]

logoutComponent: Html Msg
logoutComponent = div [class "alpha grid_6 omega" ] [text "Logout"]

loginPage: State -> Html Msg
loginPage state =
    div [] [text "Login"]

-- Subscriptions
subscriptions: UiState -> Sub Msg
subscriptions state =
    Sub.none