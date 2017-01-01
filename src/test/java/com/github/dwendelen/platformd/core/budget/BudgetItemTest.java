package com.github.dwendelen.platformd.core.budget;

import com.github.dwendelen.platformd.core.budget.command.CreateBudgetItem;
import com.github.dwendelen.platformd.core.budget.event.BudgetItemCreated;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Test;

import static org.junit.Assert.*;

public class BudgetItemTest {
    public static final String NAME = "name";
    private AggregateTestFixture<BudgetItem> fixture =
            new AggregateTestFixture<>(BudgetItem.class);

    @Test
    public void creation() {
        CreateBudgetItem createBudgetItem = new CreateBudgetItem();
        createBudgetItem.name = NAME;

        BudgetItemCreated budgetItemCreated = new BudgetItemCreated();
        budgetItemCreated.name = NAME;
        budgetItemCreated.uuid = createBudgetItem.uuid;

        fixture.when(createBudgetItem)
                .expectEvents(budgetItemCreated);
    }
}