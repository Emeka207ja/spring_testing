package com.imprint.springboot_testing.Dto.Employee;

import com.imprint.springboot_testing.model.Store;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateEmployeeResponse {
    private  Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Store store;
}
