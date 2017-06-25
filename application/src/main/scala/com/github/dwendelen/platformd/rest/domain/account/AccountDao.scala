package com.github.dwendelen.platformd.rest.domain.account

import java.util.UUID

import com.datastax.driver.mapping.{MappingManager, Result}
import com.datastax.driver.mapping.annotations.{Accessor, Query}
import com.github.dwendelen.platformd.core.account.{MoneyMade, NormalAccountCreated}
import com.github.dwendelen.platformd.infrastructure.authentication.IdentityProvider
import com.github.dwendelen.platformd.infrastructure.cqrs.{CQRS, Listener}
import org.springframework.stereotype.Component

import scala.collection.JavaConverters._

/*
CREATE TABLE account (
	user_id timeuuid,
	account_id timeuuid,
	account_name text,
	balance decimal,
	primary key (user_id, account_id)
);

CREATE TABLE transaction (
	account_id timeuuid,
	transaction_date date,
	transaction_id timeuuid,
	amount decimal,
    budget_item timeuuid,
	comment text,
	primary key (account_id, transaction_date, transaction_id)
) WITH CLUSTERING ORDER BY (transaction_date DESC, transaction_id DESC);
 */

@Component
class AccountDao(
                   val mappingManager: MappingManager,
                   var identityProvider: IdentityProvider,
                   val cqrs: CQRS) extends Listener {
    cqrs.registerListener(this)
    val accountMapper = mappingManager.mapper (classOf[Account] )
    val transactionMapper = mappingManager.mapper (classOf[Transaction] )
    val accountAccessor = mappingManager.createAccessor (classOf[AccountAccessor] )

    def getAccounts (userId: UUID): List[Account] = {
        accountAccessor.getAccounts(userId).all.asScala.toList
    }
    def getAccount (userId: UUID, uuid: UUID): Account = {
        accountMapper.get (userId, uuid)
    }

    def getTransactions (account: UUID): List[Transaction] = {
        accountAccessor.getTransactions (account).all.asScala.toList
    }

    private def on (normalAccountCreated: NormalAccountCreated) {
        val newAccount: Account = new Account(
            normalAccountCreated.owner,
            normalAccountCreated.uuid,
            normalAccountCreated.name,
            BigDecimal(0))
        accountMapper.save (newAccount)
    }
    private def on (event: MoneyMade) {
        val account: Account = accountMapper.get (identityProvider.getCurrentUser.user_Id, event.accountId)
        account.balance = event.newBalance.bigDecimal
        accountMapper.save (account)
        val newTransaction: Transaction = new Transaction (
            event.accountId,
            event.transactionDate,
            event.transactionId,
            event.incomeSource,
            event.amount,
            event.comment)
        transactionMapper.save (newTransaction)
    }

    override def handle(event: Any): Unit = event match {
        case e: NormalAccountCreated => on(e)
        case e: MoneyMade => on(e)
        case _ =>
    }
}
@Accessor
trait AccountAccessor {
    @Query("SELECT * FROM transaction WHERE account_id=:arg0") def getTransactions(accountId: UUID): Result[Transaction]

    @Query("SELECT * FROM account WHERE user_id=:arg0") def getAccounts(userId: UUID): Result[Account]
}