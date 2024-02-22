package com.imprint.springboot_testing.service;

import com.imprint.springboot_testing.Dto.Employee.CreateEmployeeDto;
import com.imprint.springboot_testing.Dto.Employee.CreateEmployeeResponse;
import com.imprint.springboot_testing.exception.ResourceExistException;
import com.imprint.springboot_testing.model.Employee;
import com.imprint.springboot_testing.model.Store;
import com.imprint.springboot_testing.repository.EmployeeRepository;
import com.imprint.springboot_testing.repository.StoreRepository;
import com.imprint.springboot_testing.service.Impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    private Store store;
    private CreateEmployeeDto createEmployeeDto;
    private CreateEmployeeResponse createEmployeeResponse;
    @BeforeEach
    public void setup(){
        createEmployeeDto = new CreateEmployeeDto();
        createEmployeeDto.setEmail("bright@gmail.com");
        createEmployeeDto.setFirstname("bright");
        createEmployeeDto.setLastname("asiwe");

        store = Store.builder()
                .storeId(1L)
                .name("bright store")
                .employees(new ArrayList<>())
                .build();


    }

    @DisplayName("Junit test for save employee when employee email does not exist on db already")
    @Test
    public  void givenEmployeeObject_whenSave_thenReturnsEmployeeObject(){

        BDDMockito.given(storeRepository.findById(1L)).willReturn(Optional.of(store));
        Employee employee = Employee.builder()
                .id(1L)
                .email(createEmployeeDto.getEmail())
                .lastname(createEmployeeDto.getLastname())
                .firstname(createEmployeeDto.getFirstname())
                .store(store)
                .build();

        BDDMockito.given(employeeRepository.save(Mockito.any(Employee.class))).willReturn(employee);
        BDDMockito.given(employeeRepository.existsByEmail(createEmployeeDto.getEmail())).willReturn(false);

        Employee response = this.employeeService.createEmployee(createEmployeeDto,1L);

        Assertions.assertThat(response.getEmail()).isEqualTo("bright@gmail.com");
        System.out.println(response.getStore());
         Assertions.assertThat(response.getStore()).isNotNull();
        Assertions.assertThat(response.getStore().getName()).isEqualTo("bright store");

    }
    @DisplayName("Junit test for save employee returns exception when email exists")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnsException(){
        BDDMockito.given(employeeRepository.existsByEmail(createEmployeeDto.getEmail())).willReturn(true);
        org.junit.jupiter.api.Assertions.assertThrows(ResourceExistException.class,()->employeeService.createEmployee(createEmployeeDto,1L));
    }
}
