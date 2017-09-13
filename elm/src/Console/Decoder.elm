module Console.Decoder exposing (ctor, string, int, end)

type alias Decoder a = List String -> Result String a

ctor: a -> (a -> Decoder r) -> Decoder r
ctor ctor nextInChain =
  nextInChain ctor

string: (a -> Decoder r) -> (String -> a) -> Decoder r
string nextInChain acc =
  parser Ok nextInChain acc

int: (a -> Decoder r) -> (Int -> a) -> Decoder r
int nextInChain acc =
  parser String.toInt nextInChain acc

parser: (String -> Result String typ) -> (a -> Decoder r) -> (typ -> a) -> Decoder r
parser stringToType nextInChain acc list =
  case list of
    [] ->
      Err "Not enough arguments"
    head::tail ->
      stringToType head
      |> Result.andThen (\convertedToType -> nextInChain (acc convertedToType) tail)

end: r -> Decoder r
end acc list =
  case list of
    [] -> Ok acc
    _ -> Err "Too many arguments"