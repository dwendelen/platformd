package com.github.dwendelen.platformd.core.budget

import java.util.UUID

import com.fasterxml.jackson.annotation.JsonCreator
import com.github.dwendelen.platformd.infrastructure.cqrs.{Aggregate, AggregateResult, CommandSucces}

class IncomeSource(uuid: UUID) extends Aggregate[IncomeSource] {
    def this() = this(null)

    override def applyEvent(event: Any): IncomeSource = event match {
        case e: IncomeSourceCreated => applyEvent(e)
        case _ => this
    }

    def applyEvent(event: IncomeSourceCreated): IncomeSource =
        new IncomeSource(event.uuid)

    override def handle(command: Any): AggregateResult = command match {
        case cmd: CreateIncomeSource => AggregateResult(CommandSucces(cmd.uuid),handle(cmd))
        case cmd: RecordIncome => AggregateResult(CommandSucces(uuid), handle(cmd))
    }
    def handle(cmd: CreateIncomeSource): List[Any] =
        List(new IncomeSourceCreated(
            cmd.uuid,
            cmd.owner,
            cmd.name
        ))

    def handle(cmd: RecordIncome): List[Any] =
        List(new IncomeRecorded(
            cmd.incomeSource,
            cmd.transactionId,
            cmd.amount,
            cmd.accountId
        ))
}

//Events
@JsonCreator
class IncomeSourceCreated(val uuid: UUID,
                          val owner: UUID,
                          val name: String)
@JsonCreator
class IncomeRecorded(val incomeSource: UUID,
                     val transactionId: UUID,
                     val amount: BigDecimal,
                     val accountId: UUID)

//Commands
@JsonCreator
class CreateIncomeSource(val  uuid: UUID,
                         val owner: UUID,
                         val name: String)
@JsonCreator
class RecordIncome(val incomeSource: UUID,
                   val transactionId: UUID,
                   val amount: BigDecimal,
                   val accountId: UUID)