package com.github.dwendelen.platformd.rest.domain.budget

import java.util.UUID

import com.datastax.driver.mapping.annotations.{ClusteringColumn, Column, PartitionKey, Table}

import scala.annotation.meta.field
import scala.beans.BeanProperty

@Table(name = "expense_post") class ExpensePost(
    @(PartitionKey @field)
    @(Column @field)(name = "user_id")
    @BeanProperty
    var userId: UUID,
    @(ClusteringColumn @field)
    @(Column @field)(name = "expense_post_id")
    @BeanProperty
    var uuid: UUID,
    @(Column @field)(name = "name")
    @BeanProperty
    var name: String
){
    def this() = this(null, null, null)
}