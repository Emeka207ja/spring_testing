package com.imprint.springboot_testing.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imprint.springboot_testing.Dto.Employee.CreateEmployeeDto;
import com.imprint.springboot_testing.Dto.Employee.CreateEmployeeResponse;
import com.imprint.springboot_testing.model.Employee;
import com.imprint.springboot_testing.model.Store;
import com.imprint.springboot_testing.repository.EmployeeRepository;
import com.imprint.springboot_testing.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;
    private CreateEmployeeDto createEmployeeDto;
    private CreateEmployeeDto updateEmployeeDto;
    @BeforeEach
    void setup(){
        employeeRepository.deleteAll();
    }
    @DisplayName("integration test for create employee")
    @Test
    public  void createEmployee() throws Exception{
        createEmployeeDto = new CreateEmployeeDto();
        createEmployeeDto.setFirstname("bright");
        createEmployeeDto.setLastname("asiwe");
        createEmployeeDto.setEmail("asiwes@gmail.com");

        CreateEmployeeResponse createEmployeeResponse = CreateEmployeeResponse.builder()
                .message("created")
                .build();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/employee/create_employee/{storeId}",4L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createEmployeeDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(createEmployeeResponse.getMessage()))
                .andDo(MockMvcResultHandlers.print());

        System.out.println(resultActions);
    }
}
