package com.imprint.springboot_testing.service.Impl;

import com.imprint.springboot_testing.Dto.Employee.CreateEmployeeDto;
import com.imprint.springboot_testing.Dto.Employee.CreateEmployeeResponse;
import com.imprint.springboot_testing.exception.ResourceExistException;
import com.imprint.springboot_testing.exception.ResourceNotFoundException;
import com.imprint.springboot_testing.model.Employee;
import com.imprint.springboot_testing.model.Store;
import com.imprint.springboot_testing.repository.EmployeeRepository;
import com.imprint.springboot_testing.repository.StoreRepository;
import com.imprint.springboot_testing.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private StoreRepository storeRepository;



    @Override
    public CreateEmployeeResponse createEmployee(CreateEmployeeDto createEmployeeDto,Long storeId) {
        if(employeeExists(createEmployeeDto.getEmail())){
            throw  new ResourceExistException("employee email taken");
        }
        Store store = findStoreById(storeId);
        System.out.println(store.getName());
        Employee employee = Employee.builder()
                .email(createEmployeeDto.getEmail())
                .store(store)
                .firstname(createEmployeeDto.getFirstname())
                .lastname(createEmployeeDto.getLastname())
                .build();
        employeeRepository.save(employee);
        return CreateEmployeeResponse.builder()
                .message("created")
                .build();
    }

    @Override
    public Employee getEmployee(Long employeeId) {
        return findEmployeeById(employeeId);
    }

    @Override
    public Employee updateEmployee(CreateEmployeeDto createEmployeeDto, Long employeeId) {
        Employee employee = findEmployeeById(employeeId);
        System.out.println(employeeId);
        System.out.println(employee.getId());
        if (createEmployeeDto.getEmail() != null){
            employee.setEmail(createEmployeeDto.getEmail());
        }
        if (createEmployeeDto.getLastname() != null){
            employee.setLastname(createEmployeeDto.getLastname());
        }
        if (createEmployeeDto.getFirstname() != null){
            employee.setFirstname(createEmployeeDto.getFirstname());
        }
        return employeeRepository.save(employee);
    }

    private Store findStoreById(Long storeId){
        return storeRepository.findById(storeId).orElseThrow(()->new ResourceNotFoundException("store not found"));
    }
    private Boolean employeeExists(String employeeEmail){
        return employeeRepository.existsByEmail(employeeEmail);
    }
    private Employee findEmployeeById(Long employeeId){
        return employeeRepository.findById(employeeId).orElseThrow(()->new ResourceNotFoundException("employee does not exist"));
    }
    private Store mapStoreToEmployee(Store store){
        if(store != null){
            return  Store.builder()
                    .storeId(store.getStoreId())
                    .name(store.getName())
                    .build();
        }
        return null;
    }
}
