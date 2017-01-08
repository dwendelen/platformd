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


@Component
public class BudgetDao {
    private IncomeSourceAccessor incomeSourceAccessor;
    private Mapper<IncomeSource> incomeSourceMapper;

    @Autowired
    private BudgetDao(MappingManager mappingManager) {
        incomeSourceMapper = mappingManager.mapper(IncomeSource.class);
        incomeSourceAccessor = mappingManager.createAccessor(IncomeSourceAccessor.class);
    }

    public List<IncomeSource> getAll() {
        return incomeSourceAccessor.getAllTransactions().all();
    }

    public IncomeSource getIncomeSource(UUID uuid) {
        return incomeSourceMapper.get(uuid);
    }

    @EventHandler
    public void on(IncomeSourceCreated event) {
        incomeSourceMapper.save(new IncomeSource()
            .setUuid(event.getUuid())
            .setName(event.getName())
        );
    }

    @Accessor
    public interface IncomeSourceAccessor {
        @Query("SELECT * FROM income_source")
        Result<IncomeSource> getAllTransactions();
    }
}
