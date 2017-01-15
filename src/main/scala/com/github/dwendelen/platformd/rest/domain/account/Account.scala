package com.github.dwendelen.platformd.rest.domain.account

import java.time.LocalDate
import java.util.UUID

import com.datastax.driver.mapping.annotations._
import com.fasterxml.jackson.annotation.JsonFormat

import scala.annotation.meta.field
import scala.beans.BeanProperty

@Table(name = "account")
class Account(
                 @(PartitionKey @field)
                 @(Column @field)(name = "user_id")
                 @BeanProperty
                 var userId: UUID,
                 @(ClusteringColumn @field)
                 @(Column @field)(name = "account_id")
                 @BeanProperty
                 var accountId: UUID,
                 @(Column @field)(name = "account_name")
                 @BeanProperty
                 var name: String,
                 @(Column @field)(name = "balance")
                 @BeanProperty
                 var balance: BigDecimal
             ) {
    def this() = this(null, null, null, null)
}

@Table(name = "transaction")
class Transaction(
                     @(PartitionKey @field)
                     @(Column @field)(name = "account_id")
                     @BeanProperty
                     var accountId: UUID,
                     @ClusteringColumn(0)
                     @(Column @field)(name = "transaction_date")
                     @(JsonFormat @field)(shape = JsonFormat.Shape.STRING)
                     @BeanProperty
                     var transactionDate: LocalDate,
                     @ClusteringColumn(1)
                     @(Column @field)(name = "transaction_id")
                     @BeanProperty
                     var transactionId: UUID,
                     @(Column @field)(name = "budget_item")
                     @BeanProperty
                     var budgetItem: UUID,
                     @(Column @field)(name = "amount")
                     @BeanProperty
                     var amount: BigDecimal,
                     @(Column @field)(name = "comment")
                 @BeanProperty
                     var comment: String
                 ) {
    def this() = this(null, null, null, null, null, null)
}
