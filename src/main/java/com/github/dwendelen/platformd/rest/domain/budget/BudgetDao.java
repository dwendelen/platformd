package com.github.dwendelen.platformd.rest.domain.budget;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import com.github.dwendelen.platformd.core.budget.event.IncomeSourceCreated;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/*
CREATE TABLE income_source (
    user_id timeuuid,
    income_source_id timeuuid,
    name text,
    primary key (user_id, income_source_id)
);
 */
@Component
public class BudgetDao {
    private IncomeSourceAccessor incomeSourceAccessor;
    private Mapper<IncomeSource> incomeSourceMapper;

    @Autowired
    private BudgetDao(MappingManager mappingManager) {
        incomeSourceMapper = mappingManager.mapper(IncomeSource.class);
        incomeSourceAccessor = mappingManager.createAccessor(IncomeSourceAccessor.class);
    }

    public List<IncomeSource> getAll(UUID userId) {
        return incomeSourceAccessor.getAllTransactions(userId).all();
    }

    public IncomeSource getIncomeSource(UUID userId, UUID uuid) {
        return incomeSourceMapper.get(userId, uuid);
    }

    @EventHandler
    public void on(IncomeSourceCreated event) {
        incomeSourceMapper.save(new IncomeSource()
                .setUserId(event.getOwner())
                .setUuid(event.getUuid())
                .setName(event.getName())
        );
    }

    @Accessor
    public interface IncomeSourceAccessor {
        @Query("SELECT * FROM income_source WHERE user_id=:arg0")
        Result<IncomeSource> getAllTransactions(UUID userId);
    }
}
