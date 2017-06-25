package com.github.dwendelen.platformd.rest

import com.datastax.driver.core.utils.UUIDs
import com.github.dwendelen.platformd.core.budget.{IncomeSource => IncomeSourceA}
import com.github.dwendelen.platformd.core.budget.CreateIncomeSource
import com.github.dwendelen.platformd.infrastructure.cqrs.CQRS
import com.github.dwendelen.platformd.rest.domain.budget.BudgetDao
import com.github.dwendelen.platformd.rest.domain.budget.IncomeSource
import org.springframework.web.bind.annotation._
import java.util.List
import java.util.UUID

/**
  * Created by xtrit on 1/01/17.
  */
@RestController
@RequestMapping(Array("/api/users/{userId}/budget"))
class BudgetItemController(
                    budgetDao: BudgetDao,
                    cqrs: CQRS) {

    @RequestMapping(method = Array(RequestMethod.GET))
    def getAll(@PathVariable("userId") userId: UUID): List[IncomeSource] =
        budgetDao.getAll(userId)

    @RequestMapping(method = Array(RequestMethod.POST))
    def create(@PathVariable("userId") userId: UUID,
               @RequestBody incomeSource: IncomeSource): IncomeSource = {
        val createIncomeSource = new CreateIncomeSource(UUIDs.timeBased, userId, incomeSource.getName)
        cqrs.execute(createIncomeSource.uuid, createIncomeSource, classOf[IncomeSourceA])
        budgetDao.getIncomeSource(userId, createIncomeSource.uuid)
    }
}