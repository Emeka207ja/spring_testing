package com.imprint.springboot_testing.repository;

import com.imprint.springboot_testing.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;

import com.imprint.springboot_testing.model.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private StoreRepository storeRepository;
    private Employee employee;
    private Store store;
    @BeforeEach
    public void setup(){
         employee = Employee.builder()
            .email("asiwebrightemeka@gmail.com")
            .firstname("bright")
            .lastname("Asiwe")
            .build();

         store = Store.builder()
                 .name("emy_store")
                 .build();
    }

    @DisplayName("Junit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnsSavedEmployeeObject(){
        Employee savedEmployee = this.employeeRepository.save(employee);
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @DisplayName("Junit test for find all employee")
    @Test
    public void givenEmployeeList_whenFindAll_thenReturnsEmployeesList(){
        Employee employee2 = Employee.builder()
                .email("brightemeka@gmail.com")
                .firstname("testName")
                .lastname("Asiwe")
                .build();

        this.employeeRepository.save(employee);
        this.employeeRepository.save(employee2);
        List<Employee> employeeList = this.employeeRepository.findAll();
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).size().isEqualTo(2);
    }
    @DisplayName("junit test for get employee by id operation")
    @Test
    public void givenEmployeeId_whenFindById_thenReturnsEmployee(){
        Employee savedEmployee = this.employeeRepository.save(employee);
        Employee result = this.employeeRepository.findById(savedEmployee.getId()).get();
        assertThat(result).isNotNull();
    }
    @DisplayName("junit test for find employee by email")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployee(){
        Employee savedEmployee = employeeRepository.save(employee);
        Optional<Employee> result = employeeRepository.findByEmail(savedEmployee.getEmail());
        assertThat(result).isNotNull();
    }
    @DisplayName("junit test for delete employee by id operation")
    @Test
    public void givenEmployeeId_whenDeleteById_thenRemoveEmployee(){
        Employee savedEmployee = this.employeeRepository.save(employee);
        this.employeeRepository.deleteById(savedEmployee.getId());
        Optional<Employee> result = this.employeeRepository.findById(savedEmployee.getId());
        assertThat(result).isEmpty();
    }
    @DisplayName("junit test for update employee")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnEmployee(){
        Employee savedEmployee = employeeRepository.save(employee);
        Employee employee1 = employeeRepository.findById(savedEmployee.getId()).get();
        employee1.setEmail("bright@gmail.com");
        employee1.setFirstname("emy");
        Employee result = employeeRepository.save(employee1);
        assertThat(savedEmployee.getEmail()).isEqualTo("bright@gmail.com");
    }
    @DisplayName("junit test for find employee by email and store")
    @Test
    public void givenEmployeeEmailAndStore_whenFindEmployeeByEmailAndStore_thenReturnEmployeeObject(){
        Store emyStore = storeRepository.save(store);
        employee.setStore(emyStore);
        Employee employee1 = employeeRepository.save(employee);
        Employee result = employeeRepository.findByEmailAndStore(employee1.getEmail(),store).get();
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(employee1.getEmail());
        assertThat(result.getStore()).isNotNull();
        assertThat(result.getStore().getStoreId()).isEqualTo(emyStore.getStoreId());
    }
}
