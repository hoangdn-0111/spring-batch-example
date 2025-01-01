package org.example.springbatch.batch.listener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbatch.constants.Constants;
import org.example.springbatch.entity.BatchEntity;
import org.example.springbatch.repository.BatchEntityRepository;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class TriggerFunctionListener implements JobExecutionListener {
    private final BatchEntityRepository batchEntityRepository;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("beforeJob {}", jobExecution);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("afterJob TriggerFunctionBatch job with status {}", jobExecution.getStatus());

        Long id = jobExecution.getJobParameters().getLong(Constants.BATCH_ENTITY_ID_KEY);
        if (id == null) {
            log.error("id is null");
            return;
        }

        Optional<BatchEntity> entityOptional = this.batchEntityRepository.findById(id);
        if (entityOptional.isPresent()) {
            BatchEntity batch = entityOptional.get();
            if (jobExecution.getStatus() != BatchStatus.COMPLETED) {
                batch.setStatus("FAILED");
                this.batchEntityRepository.save(batch);
            }
        }
    }
}
