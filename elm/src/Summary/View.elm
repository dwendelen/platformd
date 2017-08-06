module Summary.View exposing (accountSummary)

import Html exposing (..)
import Html.Attributes exposing (..)
import Summary.Types exposing (Summary)
import Types exposing (Msg)

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