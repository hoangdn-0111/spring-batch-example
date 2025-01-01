package org.example.springbatch.service;

public interface ReportService {
    String exportReport(String reportFormat) throws RuntimeException;
}
