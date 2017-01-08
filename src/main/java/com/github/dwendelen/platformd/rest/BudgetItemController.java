package com.github.dwendelen.platformd.rest;

import com.github.dwendelen.platformd.core.budget.command.CreateIncomeSource;
import com.github.dwendelen.platformd.core.budget.event.IncomeSourceCreated;
import com.github.dwendelen.platformd.rest.domain.budget.ExpensePost;
import com.github.dwendelen.platformd.rest.domain.budget.BudgetDao;
import com.github.dwendelen.platformd.rest.domain.budget.IncomeSource;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Created by xtrit on 1/01/17.
 */
@RestController
@RequestMapping("/api/budget")
public class BudgetItemController {
    @Autowired
    private BudgetDao readBudgetItemDao;

    @Autowired
    private CommandGateway commandGateway;

    @RequestMapping(method = RequestMethod.GET)
    public List<IncomeSource> getAll() {
        return readBudgetItemDao.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public IncomeSource create(@RequestBody IncomeSource incomeSource) {
        UUID uuid = commandGateway.sendAndWait(new CreateIncomeSource()
            .setName(incomeSource.getName()));
        return readBudgetItemDao.getIncomeSource(uuid);
    }
}
