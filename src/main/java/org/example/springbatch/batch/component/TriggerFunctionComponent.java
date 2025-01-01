package org.example.springbatch.batch.component;

import org.example.springbatch.repository.BatchEntityRepository;
import org.example.springbatch.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

@Component
public class TriggerFunctionComponent extends TriggerFunctionWriterComponent {

    public TriggerFunctionComponent(BatchEntityRepository batchEntityRepository, EmployeeRepository employeeRepository) {
        super(batchEntityRepository, employeeRepository);
    }
}
