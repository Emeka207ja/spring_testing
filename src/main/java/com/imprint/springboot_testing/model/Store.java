package com.imprint.springboot_testing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;
    private String name;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "store",cascade =  CascadeType.ALL)
    private List<Employee>employees;
}
