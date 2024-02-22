package com.imprint.springboot_testing.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "employee",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email","store_id"})
        }
)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String firstname;
    private String lastname;
    private String email;
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
