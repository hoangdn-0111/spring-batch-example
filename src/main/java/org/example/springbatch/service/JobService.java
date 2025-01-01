package org.example.springbatch.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;

public interface JobService {
    void run(Job job, JobParameters jobParameters);
}
