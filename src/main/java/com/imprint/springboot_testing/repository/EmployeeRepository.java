package com.imprint.springboot_testing.repository;

import com.imprint.springboot_testing.model.Employee;
import com.imprint.springboot_testing.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Boolean existsByEmail(String email);
    Optional<Employee>findByEmail(String email);
    Optional<Employee>findByEmailAndStore(String email, Store store);
}
