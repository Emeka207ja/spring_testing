package com.imprint.springboot_testing.service;

import com.imprint.springboot_testing.Dto.Employee.CreateEmployeeDto;
import com.imprint.springboot_testing.Dto.Employee.CreateEmployeeResponse;
import com.imprint.springboot_testing.model.Employee;

public interface EmployeeService {
    Employee createEmployee(CreateEmployeeDto createEmployeeDto, Long storeId);
    Employee getEmployee(Long employeeId);
    Employee updateEmployee(CreateEmployeeDto createEmployeeDto,Long employeeId);
}
