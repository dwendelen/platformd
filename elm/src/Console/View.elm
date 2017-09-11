module Console.View exposing (console)

import Console.Types exposing (ConsoleState, Msg(ConsoleKey))
import Html exposing (Attribute, Html, input, textarea)
import Html.Attributes exposing (class, value)
import Html.Events exposing (defaultOptions, on, onWithOptions)
import Json.Decode as Json


console: ConsoleState msg -> Html Msg
console consoleState =
    input [class "console container_24", onKeyPress ConsoleKey, value consoleState.line] []

onKeyPress : (String -> msg) -> Attribute msg
onKeyPress tagger =
  onWithOptions "keydown" nothing (Json.map tagger key)

key : Json.Decoder String
key =
  Json.field "key" Json.string

nothing =
    { defaultOptions
    | stopPropagation = True
    , preventDefault = True
    }
