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

public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private StoreRepository storeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, StoreRepository storeRepository) {
        this.employeeRepository = employeeRepository;
        this.storeRepository = storeRepository;
    }

    @Override
    public Employee createEmployee(CreateEmployeeDto createEmployeeDto,Long storeId) {
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
        Employee savedEmployee  = employeeRepository.save(employee);
//        System.out.println(savedEmployee.getStore().getName());
        return  savedEmployee;
//        Employee savedEmployee = employeeRepository.save(employee);
//        System.out.println(store.getName());
//        System.out.println(savedEmployee.getEmail());

//        return CreateEmployeeResponse.builder()
//                .id(savedEmployee.getId())
//                .email(savedEmployee.getEmail())
//                .firstname(savedEmployee.getFirstname())
//                .lastname(savedEmployee.getLastname())
//                .store(mapStoreToEmployee(savedEmployee.getStore()))
//                .build();
    }
    private Store findStoreById(Long storeId){
        return storeRepository.findById(storeId).orElseThrow(()->new ResourceNotFoundException("store not found"));
    }
    private Boolean employeeExists(String employeeEmail){
        return employeeRepository.existsByEmail(employeeEmail);
    }
    private Store mapStoreToEmployee(Store store){
        if(store != null){
            System.out.println("store id : "+store.getStoreId());
            return  Store.builder()
                    .storeId(store.getStoreId())
                    .name(store.getName())
                    .build();
        }
        return null;
    }
}
