package org.example.springbatch.service;

import org.example.springbatch.dto.EmployeeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    Page<EmployeeDto> getAllEmployees(Pageable pageable);
    String saveEmployee(List<EmployeeDto> employeeDto);
}
