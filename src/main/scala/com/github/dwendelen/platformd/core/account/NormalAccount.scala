package com.github.dwendelen.platformd.core.account

import java.time.LocalDate
import java.util.UUID

import com.fasterxml.jackson.annotation.{JsonCreator, JsonTypeInfo}
import com.github.dwendelen.platformd.infrastructure.cqrs.{Aggregate, AggregateResult, CommandSucces}

class NormalAccount(uuid: UUID,
                    balance: BigDecimal,
                    pendingTransactions: Map[UUID, Transaction]) extends Aggregate[NormalAccount]{
def this() = this(null, null,Map())
    def applyNAC(normalAccountCreated: NormalAccountCreated): NormalAccount = {
        new NormalAccount(normalAccountCreated.uuid, BigDecimal(0), Map())
    }

    def applyMM(moneyMade: MoneyMade): NormalAccount = {
        val newPending = pendingTransactions - moneyMade.transactionId

        new NormalAccount(uuid, moneyMade.newBalance, newPending)
    }

    def applyMMR(moneyMade: MoneyMadeRejected): NormalAccount = {
        val newPending = pendingTransactions - moneyMade.transactionId

        new NormalAccount(uuid, balance, newPending)
    }

    def applyMMI(event: MoneyMadeInitiated): NormalAccount = {
        val transaction = new Transaction(
            event.incomeSource,
            event.transactionDate,
            event.amount,
            event.comment
        )
        val newPending = pendingTransactions + (event.transactionId -> transaction)
        new NormalAccount(uuid, balance, newPending)
    }

    def handleCNA(cmd: CreateNormalAccount): List[Any] =
        List(new NormalAccountCreated(
            cmd.uuid,
            cmd.name,
            cmd.owner
        ))
    def handleMM(cmd: MakeMoney): List[Any] =
        List(new MoneyMadeInitiated(
            cmd.accountId,
            cmd.transactionId,
            cmd.transactionDate,
            cmd.amount,
            cmd.incomeSource,
            cmd.comment
        ))
    def handleRMM(cmd: RejectMoneyMade): List[Any] =
        List(new MoneyMadeRejected(
            cmd.accountId,
            cmd.transactionId,
            cmd.reason
        ))
    def handleAMM(cmd: AcceptMoneyMade): List[Any] = {
        val transaction: Transaction = pendingTransactions(cmd.transactionId)
        List(new MoneyMade(
            cmd.accountId,
            cmd.transactionId,
            transaction.transactionDate,
            transaction.amount,
            transaction.amount + balance,
            transaction.destination,
            transaction.comment
        ))
    }

    override def applyEvent(event: Any): NormalAccount = event match {
        case e: NormalAccountCreated => applyNAC(e)
        case e: MoneyMade => applyMM(e)
        case e: MoneyMadeRejected => applyMMR(e)
        case e: MoneyMadeInitiated => applyMMI(e)
        case _ => this
    }

    override def handle(command: Any): AggregateResult = command match {
        case c: AcceptMoneyMade => AggregateResult(CommandSucces(uuid), handleAMM(c))
        case c: CreateNormalAccount => AggregateResult(CommandSucces(c.uuid), handleCNA(c))
        case c: MakeMoney => AggregateResult(CommandSucces(uuid), handleMM(c))
        case c: RejectMoneyMade => AggregateResult(CommandSucces(uuid), handleRMM(c))
    }
}

class Transaction(val destination: UUID,
                  val transactionDate: LocalDate,
                  val amount: BigDecimal,
                  val comment: String) {
}

//Events
@JsonCreator
class NormalAccountCreated(val uuid: UUID,
                           val name: String,
                           val owner: UUID)
@JsonCreator
class MoneyMade(val accountId: UUID,
                val transactionId: UUID,
                val transactionDate: LocalDate,
                val amount: BigDecimal,
                val newBalance: BigDecimal,
                val incomeSource: UUID,
                val comment: String)
@JsonCreator
class MoneyMadeInitiated(val accountId: UUID,
                         val transactionId: UUID,
                         val transactionDate: LocalDate,
                         val amount: BigDecimal,
                         val incomeSource: UUID,
                         val comment: String)
@JsonCreator
class MoneyMadeRejected(val accountId: UUID,
                        val transactionId: UUID,
                        val reason: String)

//Commands
class AcceptMoneyMade (val accountId: UUID,
                       val transactionId: UUID)

class CreateNormalAccount (val uuid: UUID,
                           val owner: UUID,
                           val name: String)

class MakeMoney (val accountId: UUID,
                 val transactionId: UUID,
                 val transactionDate: LocalDate,
                 val amount: BigDecimal,
                 val incomeSource: UUID,
                 val comment: String)
class RejectMoneyMade(val accountId: UUID,
                      val transactionId: UUID,
                      val reason: String)