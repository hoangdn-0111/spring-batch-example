package org.example.springbatch.batch.processor;

import lombok.RequiredArgsConstructor;
import org.example.springbatch.entity.EmployeeEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
@RequiredArgsConstructor
public class TriggerFunctionProcessor implements ItemProcessor<EmployeeEntity, EmployeeEntity> {
    @Override
    public EmployeeEntity process(EmployeeEntity employee) throws Exception {
        return employee;
    }
}
