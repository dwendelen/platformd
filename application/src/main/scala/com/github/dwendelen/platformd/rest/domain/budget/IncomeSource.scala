package com.github.dwendelen.platformd.rest.domain.budget

import com.datastax.driver.mapping.annotations.{ClusteringColumn, Column, PartitionKey, Table}
import java.util.UUID

import scala.annotation.meta.field
import scala.beans.BeanProperty

@Table(name = "income_source") class IncomeSource (
    @(PartitionKey @field)
    @(Column @field)(name = "user_id")
    @BeanProperty
    var userId: UUID,
    @(ClusteringColumn @field)
    @(Column @field)(name = "income_source_id")
    @BeanProperty
    var uuid: UUID,
    @(Column @field)(name = "name")
    @BeanProperty
    var name: String
){
    def this() = this(null, null, null)
}