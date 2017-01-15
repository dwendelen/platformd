package com.github.dwendelen.platformd.rest.domain.budget

import com.datastax.driver.mapping.MappingManager
import com.datastax.driver.mapping.Result
import com.datastax.driver.mapping.annotations.Accessor
import com.datastax.driver.mapping.annotations.Query
import com.github.dwendelen.platformd.core.budget.IncomeSourceCreated
import org.springframework.stereotype.Component
import java.util.List
import java.util.UUID

import com.github.dwendelen.platformd.infrastructure.cqrs.{CQRS, Listener}

/*
CREATE TABLE income_source (
    user_id timeuuid,
    income_source_id timeuuid,
    name text,
    primary key (user_id, income_source_id)
);
 */

@Component
class BudgetDao( mappingManager: MappingManager, cqrs: CQRS) extends Listener {
    val incomeSourceMapper = mappingManager.mapper(classOf[IncomeSource])
    val incomeSourceAccessor = mappingManager.createAccessor(classOf[IncomeSourceAccessor])
    cqrs.registerListener(this)

    def getAll(userId: UUID): List[IncomeSource] =
        incomeSourceAccessor.getAllTransactions(userId).all

    def getIncomeSource(userId: UUID, uuid: UUID): IncomeSource =
        incomeSourceMapper.get(userId, uuid)

    def on(event: IncomeSourceCreated) {
        incomeSourceMapper.save(new IncomeSource()
                .setUserId(event.owner)
                .setUuid(event.uuid)
                .setName(event.name)
        )
    }

    override def handle(event: Any): Unit = event match {
        case e: IncomeSourceCreated => on(e)
        case _ =>
    }
}

@Accessor
trait IncomeSourceAccessor {
    @Query("SELECT * FROM income_source WHERE user_id=:arg0")
    def getAllTransactions(userId: UUID): Result[IncomeSource]
}