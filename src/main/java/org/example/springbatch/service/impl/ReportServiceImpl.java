package org.example.springbatch.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.springbatch.entity.EmployeeEntity;
import org.example.springbatch.repository.EmployeeRepository;
import org.example.springbatch.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final EmployeeRepository repository;

    @Override
    public String exportReport(String reportFormat) {
        String path = Paths.get(System.getProperty("user.home"), "Downloads").toString();
        List<EmployeeEntity> employees = repository.findAll();
        //load file and compile it
        try {
            File file = ResourceUtils.getFile("classpath:reports/employees.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Java Techie");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            if (reportFormat.equalsIgnoreCase("html")) {
                JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "/employees.html");
            }

            if (reportFormat.equalsIgnoreCase("pdf")) {
                JasperExportManager.exportReportToPdfFile(jasperPrint, path + "/employees.pdf");
            }
        } catch (JRException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return "report generated in path : " + path;
    }
}
