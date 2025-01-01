package org.example.springbatch.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbatch.constants.Constants;
import org.example.springbatch.dto.EmployeeDto;
import org.example.springbatch.entity.BatchEntity;
import org.example.springbatch.entity.EmployeeEntity;
import org.example.springbatch.repository.BatchEntityRepository;
import org.example.springbatch.repository.EmployeeRepository;
import org.example.springbatch.service.EmployeeService;
import org.example.springbatch.service.JobService;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final BatchEntityRepository batchEntityRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final JobService jobService;
    private final Job triggerFunctionJob;

    @Value("${tcb.retry.maxAttempts}")
    private int maxAttempts;

    @Override
    @Retryable(interceptor = "retryExampleInterceptor")
    public Page<EmployeeDto> getAllEmployees(Pageable pageable) {
        int retryCount = Objects.requireNonNull(RetrySynchronizationManager.getContext()).getRetryCount();
        log.info("getAllEmployees retry {}", retryCount);
        Page<EmployeeEntity> employees = employeeRepository.findAll(pageable);
//        if (employees.getTotalElements() > 0 && maxAttempts - 1 > retryCount) {
//            log.info("error get employees");
//            throw new AppInvalidException("Retry error");
//        }
        return employees.map(employee -> modelMapper.map(employee, EmployeeDto.class));
    }

    @Override
    public String saveEmployee(List<EmployeeDto> employeeDto) {
        BatchEntity batchEntity = new BatchEntity();
        batchEntity.setStatus("IMPORT");
        batchEntity.setCreatedBy("HoangDN");
        batchEntity.setModifiedBy("HoangDN");

        batchEntity = batchEntityRepository.save(batchEntity);
        Long batchId = batchEntity.getId();

        List<EmployeeEntity> employeeEntities = new ArrayList<>();
        employeeDto.forEach(employee -> {
            EmployeeEntity employeeEntity = new EmployeeEntity();
            employeeEntity.setStatus("IMPORT");
            employeeEntity.setDesignation(employee.getDesignation());
            employeeEntity.setName(employee.getName());
            employeeEntity.setSalary(employee.getSalary());
            employeeEntity.setDoj(employee.getDoj());
            employeeEntity.setBatchId(batchId);
            employeeEntities.add(employeeEntity);
        });

        this.employeeRepository.saveAll(employeeEntities);

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong(Constants.BATCH_ENTITY_ID_KEY, batchEntity.getId())
                .toJobParameters();

        this.jobService.run(triggerFunctionJob, jobParameters);
        return "DONE";
    }


}
