package com.github.dwendelen.platformd.rest;

import com.github.dwendelen.platformd.core.budget.command.CreateIncomeSource;
import com.github.dwendelen.platformd.core.budget.event.IncomeSourceCreated;
import com.github.dwendelen.platformd.rest.domain.budget.ExpensePost;
import com.github.dwendelen.platformd.rest.domain.budget.BudgetDao;
import com.github.dwendelen.platformd.rest.domain.budget.IncomeSource;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by xtrit on 1/01/17.
 */
@RestController
@RequestMapping("/api/users/{userId}/budget")
public class BudgetItemController {
    @Autowired
    private BudgetDao readBudgetItemDao;

    @Autowired
    private CommandGateway commandGateway;

    @RequestMapping(method = RequestMethod.GET)
    public List<IncomeSource> getAll(@PathVariable("userId") UUID userId) {
        return readBudgetItemDao.getAll(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public IncomeSource create(@PathVariable("userId") UUID userId,
                               @RequestBody IncomeSource incomeSource) {
        UUID uuid = commandGateway.sendAndWait(new CreateIncomeSource()
                .setOwner(userId)
                .setName(incomeSource.getName()));
        return readBudgetItemDao.getIncomeSource(userId, uuid);
    }
}
