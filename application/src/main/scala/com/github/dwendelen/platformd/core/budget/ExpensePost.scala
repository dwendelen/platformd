package com.github.dwendelen.platformd.core.budget

import java.util.UUID

import com.fasterxml.jackson.annotation.JsonCreator
import com.github.dwendelen.platformd.infrastructure.cqrs.{Aggregate, AggregateResult, CommandSucces}

class ExpensePost(uuid: UUID) extends Aggregate[ExpensePost] {
    def this() = this(null)

    override def applyEvent(event: Any): ExpensePost = event match {
        case e: ExpensePostCreated => applyEvent(e)
        case _ => this
    }

    def applyEvent(event: ExpensePostCreated): ExpensePost =
        new ExpensePost(event.uuid)

    override def handle(command: Any): AggregateResult = command match {
        case cmd: CreateExpensePost => AggregateResult(CommandSucces(cmd.uuid),handle(cmd))
        case cmd: RecordExpense => AggregateResult(CommandSucces(uuid), handle(cmd))
    }
    def handle(cmd: CreateExpensePost): List[Any] =
        List(new ExpensePostCreated(
            cmd.uuid,
            cmd.owner,
            cmd.name
        ))

    def handle(cmd: RecordExpense): List[Any] =
        List(new ExpenseRecorded(
            cmd.expensePost,
            cmd.transactionId,
            cmd.amount,
            cmd.accountId
        ))
}

//Events
@JsonCreator
class ExpensePostCreated(val uuid: UUID,
                          val owner: UUID,
                          val name: String)

@JsonCreator
class ExpenseRecorded(val expensePost: UUID,
                     val transactionId: UUID,
                     val amount: BigDecimal,
                     val accountId: UUID)

//Commands
@JsonCreator
class CreateExpensePost(val  uuid: UUID,
                         val owner: UUID,
                         val name: String)

@JsonCreator
class RecordExpense(val expensePost: UUID,
                   val transactionId: UUID,
                   val amount: BigDecimal,
                   val accountId: UUID)