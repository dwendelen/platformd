module Console.Types exposing (..)

type alias ConsoleState msg =
    { line: String
    , commands: List (Command msg)
    }

type Msg =
      ConsoleKey String

type alias Command msg =
    { name: String
    , argumentAutocomplete: List (List String)
    , execute: List String -> Maybe msg
    }