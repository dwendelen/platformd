package com.github.dwendelen.platformd.rest

import com.datastax.driver.core.utils.UUIDs
import com.github.dwendelen.platformd.core.account.CreateNormalAccount
import com.github.dwendelen.platformd.core.account.MakeMoney
import com.github.dwendelen.platformd.core.account.NormalAccount
import com.github.dwendelen.platformd.infrastructure.cqrs.CQRS
import com.github.dwendelen.platformd.rest.domain.account.AccountDao
import com.github.dwendelen.platformd.rest.domain.account.Account
import com.github.dwendelen.platformd.rest.domain.account.Transaction
import org.springframework.web.bind.annotation._
import java.util.UUID

import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.boot.jackson.JsonComponent

import scala.beans.BeanProperty

@RestController
@RequestMapping(value = Array("/api"))
class AccountController(accountDao: AccountDao, cqrs: CQRS) {
    @RequestMapping(value = Array("/users/{userId}/accounts"), method = Array(RequestMethod.GET))
    def getAccounts(@PathVariable userId: UUID): List[Account] = {
        accountDao.getAccounts(userId)
    }

    @RequestMapping(value = Array("/accounts/{account}/transactions"), method = Array(RequestMethod.GET))
    def getTransactions(@PathVariable("account") accountUuid: UUID): List[Transaction] = {
        accountDao.getTransactions(accountUuid)
    }

    @RequestMapping(value = Array("/accounts/{account}/transactions"), method = Array(RequestMethod.POST))
    def createTransaction(@PathVariable("account") accountUuid: UUID,
                          @RequestBody readTransaction: Transaction): Unit = {
        val makeMoney = new MakeMoney(
            accountUuid,
            UUIDs.timeBased(),
            readTransaction.transactionDate,
            readTransaction.amount,
            readTransaction.budgetItem,
            readTransaction.comment)
        cqrs.execute(accountUuid, makeMoney, classOf[NormalAccount])
    }

    @RequestMapping(value = Array("/users/{userId}/accounts"), method = Array(RequestMethod.POST))
    @ResponseBody
    def createAccount(@PathVariable userId: UUID,
                      @RequestBody createAccount: CreateAccount): Account = {
        val createNormalAccount = new CreateNormalAccount(
            UUIDs.timeBased(),
            userId,
            createAccount.name
        )

        cqrs.execute(createNormalAccount.uuid, createNormalAccount, classOf[NormalAccount])
        Thread.sleep(1000)

        accountDao.getAccount(userId, createNormalAccount.uuid)
    }

    @RequestMapping(value = Array("/{account}"), method = Array(RequestMethod.DELETE))
    def deleteAccount(@PathVariable("account") accountUuid: UUID): Unit = {
        throw new UnsupportedOperationException()
    }


}

class CreateAccount(val name: String) {
    def this() = this(null)
}