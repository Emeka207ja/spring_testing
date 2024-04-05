package com.imprint.springboot_testing.service;

import com.imprint.springboot_testing.Dto.Employee.CreateEmployeeDto;
import com.imprint.springboot_testing.Dto.Employee.CreateEmployeeResponse;
import com.imprint.springboot_testing.exception.ResourceExistException;
import com.imprint.springboot_testing.exception.ResourceNotFoundException;
import com.imprint.springboot_testing.model.Employee;
import com.imprint.springboot_testing.model.Store;
import com.imprint.springboot_testing.repository.EmployeeRepository;
import com.imprint.springboot_testing.repository.StoreRepository;
import com.imprint.springboot_testing.service.Impl.EmployeeServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static  org.junit.jupiter.api.Assertions.assertThrows;

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
    private CreateEmployeeDto updateEmployeeDto;
    private CreateEmployeeResponse createEmployeeResponse;
    private  Employee employee;
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

        employee = Employee.builder()
                .id(1L)
                .email(createEmployeeDto.getEmail())
                .lastname(createEmployeeDto.getLastname())
                .firstname(createEmployeeDto.getFirstname())
                .build();
    }

    @DisplayName("Junit test for save employee when employee email does not exist on db already")
    @Test
    public  void createEmployeeServicePC(){

        BDDMockito.given(storeRepository.findById(1L)).willReturn(Optional.of(store));
        BDDMockito.given(employeeRepository.save(Mockito.any(Employee.class))).willReturn(employee);
        BDDMockito.given(employeeRepository.existsByEmail(createEmployeeDto.getEmail())).willReturn(false);

        CreateEmployeeResponse response = this.employeeService.createEmployee(createEmployeeDto,1L);

        assertThat(response.getMessage()).isEqualTo("created");


    }
    @DisplayName("Junit test for save employee returns exception when email exists")
    @Test
    public void createEmployeeServiceNC(){
        BDDMockito.given(employeeRepository.existsByEmail(createEmployeeDto.getEmail())).willReturn(true);
        org.junit.jupiter.api.Assertions.assertThrows(ResourceExistException.class,()->employeeService.createEmployee(createEmployeeDto,1L));
    }
    @DisplayName("junit service test to update employee record (positive scenario)")
    @Test
    public  void updateEmployeePositiveScenario(){
        Long employeeId = 1L;
        BDDMockito.given(employeeRepository.save(Mockito.any(Employee.class))).willReturn(employee);
        BDDMockito.given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));
        updateEmployeeDto = new CreateEmployeeDto();
        updateEmployeeDto.setEmail("emy@gmail.com");
        updateEmployeeDto.setLastname("ema");

      Employee result = employeeService.updateEmployee(updateEmployeeDto,employeeId);
      assertThat(result.getId().toString()).isEqualTo(employee.getId().toString());
      assertThat(result.getEmail()).isEqualTo("emy@gmail.com");
      assertThat(result.getFirstname()).isEqualTo("bright");
      assertThat(result.getLastname()).isEqualTo("ema");

    }
    @DisplayName("junit service test to update employee (negative scenario)")
    @Test
    public void updateEmployeeNegativeScenario(){
        updateEmployeeDto = new CreateEmployeeDto();
        updateEmployeeDto.setEmail("emy@gmail.com");
        updateEmployeeDto.setLastname("ema");
        Long employeeId = 1L;
        BDDMockito.given(employeeRepository.findById(employeeId)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,()->employeeService.updateEmployee(updateEmployeeDto,employeeId));

    }
}
