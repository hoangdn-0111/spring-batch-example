package org.example.springbatch.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbatch.anotation.BasicAuthRequired;
import org.example.springbatch.dto.EmployeeDto;
import org.example.springbatch.service.EmployeeService;
import org.example.springbatch.service.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final ReportService service;
    private final EmployeeService employeeService;

    @GetMapping
    @BasicAuthRequired(clientName = "pp")
    public ResponseEntity<Page<EmployeeDto>> getEmployees(Pageable pageable) {
        return ResponseEntity.ok(employeeService.getAllEmployees(pageable));
    }

    @GetMapping("/report")
    public ResponseEntity<String> generateReport(@RequestParam(name = "format") String format) {
        return ResponseEntity.ok(service.exportReport(format));
    }

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody List<EmployeeDto> employeeDto) {
        return ResponseEntity.ok(employeeService.saveEmployee(employeeDto));
    }
}
