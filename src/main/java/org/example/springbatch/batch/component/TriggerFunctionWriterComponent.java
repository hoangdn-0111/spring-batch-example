package org.example.springbatch.batch.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbatch.entity.BatchEntity;
import org.example.springbatch.entity.EmployeeEntity;
import org.example.springbatch.exception.type.BadRequestException;
import org.example.springbatch.repository.BatchEntityRepository;
import org.example.springbatch.repository.EmployeeRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class TriggerFunctionWriterComponent {
    protected final BatchEntityRepository batchEntityRepository;
    protected final EmployeeRepository employeeRepository;

    public void write(Long batchId, List<? extends EmployeeEntity> list) {
        BatchEntity batchEntity = batchEntityRepository.findById(batchId).orElseThrow(BadRequestException::new);
        list.forEach(employee -> {
            employee.setStatus("SUCCESS");
        });
        this.employeeRepository.saveAll(list);

        batchEntity.setStatus("DONE");
        this.batchEntityRepository.save(batchEntity);
    }
}
