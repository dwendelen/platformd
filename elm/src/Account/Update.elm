module Account.Update exposing (update)

import Account.Types exposing (..)

update: Msg->List Transaction->List Transaction
update msg transactions =
    case msg of
        ExpandComplex uuid -> apply (\t -> {t | expanded = True}) uuid transactions
        CollapseComplex uuid -> apply (\t -> {t | expanded = False}) uuid transactions

apply: (ComplexTransactionData -> ComplexTransactionData) -> String -> List Transaction -> List Transaction
apply function uuid transactions =
    List.map (applySingle function uuid) transactions

applySingle: (ComplexTransactionData -> ComplexTransactionData) -> String -> Transaction -> Transaction
applySingle function uuid transaction =
    case transaction of
        SimpleTransaction simple -> SimpleTransaction simple
        ComplexTransaction complex ->
            if complex.uuid == uuid then
                ComplexTransaction (function complex)
            else
                ComplexTransaction complex