package com.imprint.springboot_testing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imprint.springboot_testing.Dto.Employee.CreateEmployeeDto;
import com.imprint.springboot_testing.exception.ResourceNotFoundException;
import com.imprint.springboot_testing.model.Employee;
import com.imprint.springboot_testing.model.Store;
import com.imprint.springboot_testing.service.EmployeeService;
import net.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest
public class EmployeeTestController {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;
    private CreateEmployeeDto createEmployeeDto;
    private CreateEmployeeDto updateEmployeeDto;
    private Store store;
    private  Employee employee;
    @BeforeEach
    void setup(){
         createEmployeeDto = new CreateEmployeeDto();
        createEmployeeDto.setFirstname("bright");
        createEmployeeDto.setLastname("asiwe");
        createEmployeeDto.setEmail("asiwe@gmail.com");

         store = Store.builder()
                .employees(new ArrayList<>())
                .name("test store")
                .storeId(1L)
                .build();

         employee = Employee.builder()
                .id(1L)
                .email(createEmployeeDto.getEmail())
                .store(store)
                .firstname(createEmployeeDto.getFirstname())
                .lastname(createEmployeeDto.getLastname())
                .build();
    }

    @DisplayName("Junit test for create employee")
    @Test
    public  void givenEmployeeObject_whenCreateEmployee_thenReturnEmployee() throws Exception{
        Long storeId = 1L;
        BDDMockito.given(employeeService.createEmployee(
                ArgumentMatchers.any(CreateEmployeeDto.class),
                ArgumentMatchers.any(Long.class))).willReturn(employee);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/employee/create_employee/{storeId}",storeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createEmployeeDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value(employee.getFirstname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(employee.getLastname()))
                .andDo(MockMvcResultHandlers.print())
        ;
        System.out.println(resultActions);
    }
    @DisplayName("junit test for getEmployee (positive case)")
    @Test
    public void givenEmployeeId_whenGetEmployee_thenReturnsEmployeeObject() throws Exception {
        Long employeeId = 1L;
        BDDMockito.given(employeeService.getEmployee(ArgumentMatchers.any(Long.class))).willReturn(employee);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/employee/get_employee/{employeeId}",employeeId)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(employee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.store").value(employee.getStore()))
                .andDo(MockMvcResultHandlers.print())
        ;
    }
    @DisplayName("junit test for get employee by id (negative case)")
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmployeeNotFound() throws Exception{
        Long employeeId = 1L;
        BDDMockito.when(employeeService.getEmployee(employeeId)).thenThrow(ResourceNotFoundException.class);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/employee/get_employee/{employeeId}",employeeId)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
        ;

    }
    @DisplayName("junit test for update employee (positive scenario)")
    @Test
    public void updateEmployeePositiveScenario() throws Exception{
        updateEmployeeDto = new CreateEmployeeDto();
        updateEmployeeDto.setFirstname("test");
        updateEmployeeDto.setLastname("test");
        updateEmployeeDto.setEmail("test@gmail.com");

        employee.setFirstname(updateEmployeeDto.getFirstname());
        employee.setEmail(updateEmployeeDto.getEmail());
        Long employeeId = 1L;
        BDDMockito.given(employeeService.getEmployee(employeeId)).willReturn(employee);


        BDDMockito.given(employeeService.updateEmployee(
                ArgumentMatchers.any(CreateEmployeeDto.class),
                ArgumentMatchers.any(Long.class)
        )).willReturn(employee);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/employee/update_employee/{employeeId}",employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateEmployeeDto))
        );
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(employee.getLastname()))
                .andDo(MockMvcResultHandlers.print())
        ;

    }
}
