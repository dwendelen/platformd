package com.github.dwendelen.platformd.rest;

import com.github.dwendelen.platformd.core.budget.ReadBudgetItem;
import com.github.dwendelen.platformd.core.budget.ReadBudgetItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xtrit on 1/01/17.
 */
@RestController
@RequestMapping("/budget")
public class BudgetItemController {
    @Autowired
    private ReadBudgetItemDao readBudgetItemDao;

    @RequestMapping(method = RequestMethod.GET)
    public List<ReadBudgetItem> getAccounts() {
        return readBudgetItemDao.getBudgetItems();
    }
}
