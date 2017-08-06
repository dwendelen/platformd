module Account.View exposing (accountPage)

import Exts.Html exposing (nbsp)
import Html exposing (..)
import Html.Attributes exposing (..)
import String exposing (isEmpty)
import Account.Types exposing (..)
import Html.Events exposing (onClick)
import Summary.Types exposing (Summary)
import Types exposing (State)


accountPage: State -> String -> Html Types.Msg
accountPage state accountName =
    div [] [
        div [class "alpha grid_17 omega center"] [
            h1 [class "no-top-margin"] [text "This is my account"]
        ],
        div [class "alpha grid_17 omega"] [
            button [] [text "Create"]
        ],
        div [class "alpha grid_15 suffix_2 omega transactions"] [
            div [class "alpha grid_3"] [text "Date"],
            div [class "grid_4"] [text "Account / Bucket"],
            div [class "grid_6"] [text "Comment"],
            div [class "grid_2 omega"] [text "Amount"]
        ],
        div [class "alpha grid_15 suffix_2 omega transactions"]
            (List.map transactionRow state.accountDetails),

        datalist [id "destinations"]
            (List.map dataListItem state.summaries)
    ]

transactionRow: Transaction -> Html Types.Msg
transactionRow transaction =
    case transaction of
        SimpleTransaction data -> simpleTransactionRow data
        ComplexTransaction data -> complexTransactionRow data

dataListItem: Summary -> Html Types.Msg
dataListItem summary =
    option [value summary.name] []

simpleTransactionRow: SimpleTransactionData -> Html Types.Msg
simpleTransactionRow transaction =
    div [class "alpha grid_15 omega"] [
        div [class "alpha grid_3"] [text transaction.date],
        div [class "grid_4"] [text transaction.otherAccount],
        div [class "grid_6"] [text (if isEmpty transaction.comment then nbsp else  transaction.comment)],
        div [class "grid_2 omega currency"] [text (toString transaction.amount)]
    ]

complexTransactionRow: ComplexTransactionData -> Html Types.Msg
complexTransactionRow transaction =
    div [class "alpha grid_15 omega"] (
        if transaction.expanded then
            [header transaction] ++ (items transaction)
        else
            [header transaction]
    )

header transaction =
    div [class "alpha grid_15 omega"] [
        div [class "alpha grid_3"] [text transaction.date],
        div [class "grid_4", onClick (toggleExpanded transaction)] [text "<Complex>"],
        div [class "grid_6"] [text (if isEmpty transaction.comment then nbsp else  transaction.comment)],
        div [class "grid_2 omega currency"] [text (toString transaction.amount)]
    ]

toggleExpanded transaction =
    Types.AccountMsg (
        if transaction.expanded then
            CollapseComplex transaction.uuid
        else
            ExpandComplex transaction.uuid
    )

items transaction =
    List.map complexItemRow transaction.otherItems

complexItemRow complexItem =
    div [class "alpha grid_15 omega"] [
        div [class "alpha prefix_1 grid_3"] [text complexItem.date],
        div [class "grid_4"] [text complexItem.account],
        div [class "grid_4"] [text (if isEmpty complexItem.comment then nbsp else  complexItem.comment)],
        div [class "grid_2 suffix_1 omega currency"] [text (toString complexItem.amount)]
    ]