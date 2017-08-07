module Login.Types exposing (..)

type LoginState = LoggedIn UserData
                | LoggedOut


type alias UserData =
    { token: String
    , userId: String
    }

type Msg = Login UserData
         | Logout

isLoggedIn loginState =
    case loginState of
          LoggedIn _ -> True
          LoggedOut -> False