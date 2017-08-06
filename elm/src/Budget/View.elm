module Budget.View exposing (budgetPage)

import Html exposing (..)
import Html.Attributes exposing (..)
import Types exposing (Msg, State)

budgetPage: State -> Html Msg
budgetPage _ =
    div [] [
        div [class "alpha grid_17 omega center"] [
            h1 [class "no-top-margin"] [text "Budget"]
        ],
        div [class "alpha grid_16 suffix_1 omega transactions"] [
            div [class "alpha grid_4"] [
                text "Bucket"
            ],
            div [class "grid_2 currency"] [
                text "Balance"
            ],
            div [class "grid_2 currency"] [
                text "Flow"
            ],
            div [class "grid_2 currency"] [
                text "Fixed"
            ],
            div [class "grid_2 omega currency"] [
                text "Weight"
            ]
        ],
        div [class "alpha grid_16 suffix_1 omega transactions"] [
            div [class "alpha grid_4"] [
                text "Bucket 2"
            ],
            div [class "grid_2 currency"] [
                text "12031.15"
            ],
            div [class "grid_2 currency"] [
                text "1170.00"
            ],
            div [class "grid_2 currency"] [
                text "60.00"
            ],
            div [class "grid_2 omega currency"] [
                text "121"
            ]
        ]
    ]