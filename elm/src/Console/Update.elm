module Console.Update exposing (update)

import Console.Types exposing (Command, ConsoleState, Msg(ConsoleKey))

update : Msg -> ConsoleState msg -> (ConsoleState msg, Maybe msg)
update msg state =
    case msg of
        ConsoleKey key ->
            handleKey key state


handleKey : String -> (ConsoleState msg) -> (ConsoleState msg, Maybe msg)
handleKey key state =
    case key of
        "Enter" ->
            ({state | line = ""}, execute state)
        "Tab" ->
            (autocomplete state, Nothing)
        "Backspace" ->
            ({state | line = String.dropRight 1 state.line}, Nothing)
        _ ->
            if isChar key then
                ({state | line = state.line ++ key}, Nothing)
            else
                (state, Nothing)

isChar : String -> Bool
isChar str =
    String.length str == 1

autocomplete : ConsoleState cmd -> ConsoleState cmd
autocomplete state =
    let
        eligible = state.commands
            |> List.map .name
            |> List.filter (String.startsWith state.line)
    in
        case eligible of
            head::[] ->
                {state | line = head ++ " "}
            _ ->
                state


execute : ConsoleState msg -> Maybe msg
execute state =
    let
        items = String.split " " state.line
    in
        case items of
            head::tail ->
                executeCmd state.commands head tail
            _ ->
                Nothing

executeCmd : List (Command msg) -> String -> List String -> Maybe msg
executeCmd commands command args =
    commands
        |> List.filter (\c -> c.name == command)
        |> List.head
        |> Maybe.andThen (\c -> c.execute args)

{-
execute : String -> List String -> Cmd msg
execute line commands =
    let
        items = String.split " " line
    in
        executeCmd commands items.head items.tail

executeCmd : List String -> String -> List String -> Cmd msg
executeCmd commands command args =
    let
        applicable = filter
-}