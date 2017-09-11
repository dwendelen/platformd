module Console.Commands exposing(commands)

import Console.Types exposing (Command)
import Types exposing (Msg(ChangeUrl, LoginMsg))
import Login.Types
import Task exposing (perform, succeed)

commands : List (Command Msg)
commands = [budget, logout]

budget : Command Msg
budget =
    { name = "budget"
    , argumentAutocomplete = []
    , execute = \_ -> Just (ChangeUrl "#/budget")
    }

logout : Command Msg
logout =
    { name = "logout"
    , argumentAutocomplete = []
    , execute = \_ -> Just (LoginMsg Login.Types.Logout)
    }