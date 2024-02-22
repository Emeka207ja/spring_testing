package com.imprint.springboot_testing.Dto.Employee;
import lombok.Data;

@Data
public class CreateEmployeeDto {
    private  Long id;
    private String firstname;
    private String lastname;
    private String email;
}
