import Html exposing (..)
import Html.Attributes exposing (..)
import List exposing (map)
import Maybe exposing (withDefault)
import Navigation
import State exposing (State)
import Summary exposing (Summary, accounts)
import Transaction exposing (transactions)
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
type alias UiState =
    { route: Route
    , appState: State
    }

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
type alias Url =
    String

type Msg
    = UpdateRoute Route

update : Msg->UiState->(UiState, Cmd Msg)
update msg state =
    case msg of
        UpdateRoute newRoute ->
            ({state | route = newRoute}, Cmd.none)

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
        BudgetPage -> loginPage state.appState
        AccountPage account -> accountPage state.appState account

type Route = LoginPage
           | BudgetPage
           | AccountPage String

route: Navigation.Location -> Route
route location =
    parseHash routes location
    |> withDefault LoginPage

routes =
    UrlParser.oneOf [
        UrlParser.map BudgetPage (UrlParser.s "budget"),
        UrlParser.map AccountPage (UrlParser.s "accounts" </> UrlParser.string)
    ]
accountSummary : Summary -> Html Msg
accountSummary summary =
    a [href ("#/accounts/" ++ summary.uuid)] [
        div [class "alpha grid_6 omega"] [
            div [class "alpha grid_4 name"] [text summary.name],
            div [classList [
                    ("grid_2 omega currency", True),
                    ("negative", summary.balance < 0)]
                 ] [text (toString summary.balance)]
        ]
    ]
logoutComponent: Html Msg
logoutComponent = div [class "alpha grid_6 omega" ] [text "Logout"]

loginPage: State -> Html Msg
loginPage state =
    div [] [text "Login"]

accountPage: State -> String -> Html Msg
accountPage state uuid =
    div [] [text ("account " ++ uuid)]

-- Subscriptions
subscriptions: UiState -> Sub Msg
subscriptions state =
    Sub.none