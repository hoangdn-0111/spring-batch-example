package org.example.springbatch.config;

import org.example.springbatch.exception.type.AppInvalidException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@EnableRetry
@Configuration
public class RetryConfig {
    @Value("${tcb.retry.maxAttempts}")
    private int maxAttempts;

    @Bean("retryExample")
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(2000L);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
        retryableExceptions.put(RuntimeException.class, true);
        retryableExceptions.put(AppInvalidException.class, true);
        retryableExceptions.put(IOException.class, false);
        RetryPolicy retryPolicy = new SimpleRetryPolicy(maxAttempts, retryableExceptions);
        retryTemplate.setRetryPolicy(retryPolicy);
        return retryTemplate;
    }

    @Bean("retryExampleInterceptor")
    public RetryOperationsInterceptor retryExampleInterceptor() {
        RetryOperationsInterceptor retry = new RetryOperationsInterceptor();
        retry.setRetryOperations(retryTemplate());
        return retry;
    }
}
