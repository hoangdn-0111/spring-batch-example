package org.example.springbatch.batch.writer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbatch.batch.component.TriggerFunctionWriterComponent;
import org.example.springbatch.constants.Constants;
import org.example.springbatch.entity.EmployeeEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@StepScope
@RequiredArgsConstructor
@Component
@Slf4j
public class TriggerFunctionWriter implements ItemWriter<EmployeeEntity> {
    private final List<TriggerFunctionWriterComponent> components;

    @Value("#{jobParameters[" + Constants.BATCH_ENTITY_ID_KEY + "]}")
    private Long id;

    @Override
    public void write(List<? extends EmployeeEntity> list) {
        TriggerFunctionWriterComponent component = this.components
                .stream()
                .findFirst()
                .orElseThrow(RuntimeException::new);
        component.write(id, list);
    }
}
