package org.example.springbatch.batch.reader;

import lombok.RequiredArgsConstructor;
import org.example.springbatch.constants.Constants;
import org.example.springbatch.entity.EmployeeEntity;
import org.example.springbatch.repository.EmployeeRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@StepScope
@RequiredArgsConstructor
@Component
public class TriggerFunctionReader extends RepositoryItemReader<EmployeeEntity> {
    private final EmployeeRepository employeeRepository;

    @Value("#{jobParameters[" + Constants.BATCH_ENTITY_ID_KEY + "]}")
    private Long id;

    @PostConstruct
    public void postConstruct() {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("id", Sort.Direction.ASC);
        List<Object> params = new ArrayList<>();
        params.add(id);
        this.setRepository(employeeRepository);
        this.setSort(sortMap);
        this.setArguments(params);
        this.setPageSize(50);
        this.setMethodName("findByBatchId");
    }
}
