package com.github.dwendelen.platformd.rest.domain.user

import com.datastax.driver.mapping.annotations.Column
import com.datastax.driver.mapping.annotations.PartitionKey
import com.datastax.driver.mapping.annotations.Table
import java.util.UUID

import scala.annotation.meta.field
import scala.beans.BeanProperty

@Table(name = "user")
class User (@(PartitionKey @field)
            @(Column @field)(name = "google_id")
            @BeanProperty
            var  google_Id: String,
            @(Column @field)(name = "admin")
            @BeanProperty
            var admin: Boolean,
            @(Column @field)(name = "user_id")
            @BeanProperty
    var user_Id: UUID) {

    def this() = this(null, false, null)
}