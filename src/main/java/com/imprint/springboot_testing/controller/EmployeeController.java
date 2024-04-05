package com.imprint.springboot_testing.controller;

import com.imprint.springboot_testing.Dto.Employee.CreateEmployeeDto;
import com.imprint.springboot_testing.Dto.Employee.CreateEmployeeResponse;
import com.imprint.springboot_testing.Dto.Store.CreateStoreDto;
import com.imprint.springboot_testing.model.Employee;
import com.imprint.springboot_testing.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping("/create_employee/{storeId}")
    public ResponseEntity<CreateEmployeeResponse>createEmployee(
            @RequestBody CreateEmployeeDto createEmployeeDto,
            @PathVariable("storeId") Long storeId
    ){
        return  new ResponseEntity<>(employeeService.createEmployee(createEmployeeDto,storeId), HttpStatus.CREATED);
    }
    @GetMapping("get_employee/{employeeId}")
    public ResponseEntity<Employee>getEmployee(@PathVariable("employeeId") Long employeeId){
        return new ResponseEntity<>(this.employeeService.getEmployee(employeeId),HttpStatus.OK );
    }
    @PutMapping("update_employee/{employeeId}")
    public ResponseEntity<Employee>updateEmployee(@RequestBody CreateEmployeeDto createEmployeeDto, @PathVariable("employeeId") Long employeeId){
        return new ResponseEntity<>(this.employeeService.updateEmployee(createEmployeeDto,employeeId),HttpStatus.OK );
    }
}
