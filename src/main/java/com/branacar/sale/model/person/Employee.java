package com.branacar.sale.model.person;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity @Table(name = "employees")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Employee {

    @Id
    private UUID employeeId;

    @Column(nullable = false)
    private String name;

    private String role;
}