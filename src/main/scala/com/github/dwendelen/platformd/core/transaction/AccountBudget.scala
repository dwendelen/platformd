package com.github.dwendelen.platformd.core.transaction

import com.github.dwendelen.platformd.core.account.{AcceptMoneyMade, MoneyMadeInitiated, NormalAccount, RejectMoneyMade}
import com.github.dwendelen.platformd.core.budget.{IncomeRecorded, IncomeSource, RecordIncome}
import com.github.dwendelen.platformd.infrastructure.cqrs.{CQRS, Listener}
import org.springframework.stereotype.Component

@Component
class AccountBudget(cqrs: CQRS) extends Listener {
    cqrs.registerListener(this)

    def on(event: MoneyMadeInitiated): Unit = {
        try {
            cqrs.execute(
                event.incomeSource,
                new RecordIncome(
                    event.incomeSource,
                    event.transactionId,
                    event.amount,
                    event.accountId),
                classOf[IncomeSource]
            )
        } catch {
            case e: Exception =>
                cqrs.execute(event.accountId,
                    new RejectMoneyMade(
                        event.accountId,
                        event.transactionId,
                        e.getMessage),
                    classOf[NormalAccount]
                )
        }
    }

    def on(event: IncomeRecorded): Unit = {
        cqrs.execute(
            event.accountId,
            new AcceptMoneyMade(
                event.accountId,
                event.transactionId),
            classOf[NormalAccount]
        )
    }

    override def handle(event: Any): Unit = event match {
        case e: MoneyMadeInitiated => on(e)
        case e: IncomeRecorded => on(e)
        case _ =>
    }
}