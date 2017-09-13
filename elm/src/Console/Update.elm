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
            let
                (newState, maybeMsg) = execute state
            in
                ({newState | line = ""} |> resetTabCount, maybeMsg)
        "Tab" ->
            (autocomplete state, Nothing)
        "Backspace" ->
            ({state | line = String.dropRight 1 state.line} |> resetTabCount, Nothing)
        _ ->
            if isChar key then
                ({state | line = state.line ++ key} |> resetTabCount, Nothing)
            else
                (state |> resetTabCount, Nothing)

isChar : String -> Bool
isChar str =
    String.length str == 1


resetTabCount : ConsoleState cmd -> ConsoleState cmd
resetTabCount state =
    {state | tabCount = 0}

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
            [] ->
                state
            _ ->
                if state.tabCount == 1 then
                    outputOptions eligible (resetTabCount state)
                else
                    {state | tabCount = state.tabCount + 1}

outputOptions eligible state =
    {state | output = String.join " " eligible}


execute : ConsoleState msg -> (ConsoleState msg, Maybe msg)
execute state =
    let
        items = String.split " " state.line
            |> List.filter (\s -> not (String.isEmpty s))
    in
        case items of
            head::tail ->
                let
                    (output, msg) = executeCmd state.commands head tail
                in
                    ({state | output = output}, msg)
            _ ->
                (state, Nothing)

executeCmd : List (Command msg) -> String -> List String -> (String, Maybe msg)
executeCmd commands command args =
    getCommand commands command
        |> Maybe.map (\c -> ("",  c.execute args))
        |> Maybe.withDefault ("Unknown command: " ++ command, Nothing)

getCommand : List (Command msg) -> String -> Maybe (Command msg)
getCommand commands command =
    commands
        |> List.filter (\c -> c.name == command)
        |> List.head