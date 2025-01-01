package org.example.springbatch.batch;

import lombok.RequiredArgsConstructor;
import org.example.springbatch.batch.listener.TriggerFunctionListener;
import org.example.springbatch.batch.processor.TriggerFunctionProcessor;
import org.example.springbatch.batch.reader.TriggerFunctionReader;
import org.example.springbatch.batch.writer.TriggerFunctionWriter;
import org.example.springbatch.entity.EmployeeEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Configuration
public class TriggerFunctionBatch {
    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;

    private final TriggerFunctionReader triggerFunctionReader;
    private final TriggerFunctionWriter triggerFunctionWriter;
    private final TriggerFunctionProcessor triggerFunctionProcessor;
    private final TriggerFunctionListener triggerFunctionListener;

    @Bean("triggerFunctionJob")
    public Job triggerFunctionJob(@Qualifier("triggerFunctionFlow") Flow flow) {
        return jobBuilderFactory
                .get("triggerFunctionJob")
                .incrementer(new RunIdIncrementer())
                .listener(triggerFunctionListener)
                .start(flow)
                .end()
                .build();
    }

    @Bean("triggerFunctionFlow")
    public Flow triggerFunctionFlow() {
        ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
        exponentialBackOffPolicy.setInitialInterval(1000);
        exponentialBackOffPolicy.setMultiplier(2.0);
        exponentialBackOffPolicy.setMaxInterval(10000);

        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
        retryableExceptions.put(RuntimeException.class, true);
        retryableExceptions.put(Exception.class, true);
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(3, retryableExceptions);

        Step step = stepBuilderFactory
                .get("triggerFunctionStep")
                .<EmployeeEntity, EmployeeEntity>chunk(50)
                .reader(triggerFunctionReader)
                .processor(triggerFunctionProcessor)
                .writer(triggerFunctionWriter)
                .faultTolerant()
                .retryPolicy(simpleRetryPolicy)
                .backOffPolicy(exponentialBackOffPolicy)
                .retry(Exception.class)
                .build();

        return new FlowBuilder<SimpleFlow>("triggerFunctionFlow").start(step).build();
    }
}
