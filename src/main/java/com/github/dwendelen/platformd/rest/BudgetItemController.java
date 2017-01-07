package com.github.dwendelen.platformd.rest;

import com.github.dwendelen.platformd.core.account.ReadAccount;
import com.github.dwendelen.platformd.core.account.command.CreateNormalAccount;
import com.github.dwendelen.platformd.core.budget.ReadBudgetItem;
import com.github.dwendelen.platformd.core.budget.ReadBudgetItemDao;
import com.github.dwendelen.platformd.core.budget.command.CreateBudgetItem;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xtrit on 1/01/17.
 */
@RestController
@RequestMapping("/api/budget")
public class BudgetItemController {
    @Autowired
    private ReadBudgetItemDao readBudgetItemDao;

    @Autowired
    private CommandGateway commandGateway;

    @RequestMapping(method = RequestMethod.GET)
    public List<ReadBudgetItem> getAccounts() {
        return readBudgetItemDao.getBudgetItems();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createAccount(@RequestBody ReadBudgetItem readAccount) {
        CreateBudgetItem createBudgetItem = new CreateBudgetItem();
        createBudgetItem.name = readAccount.name;

        commandGateway.sendAndWait(createBudgetItem);
    }
}
