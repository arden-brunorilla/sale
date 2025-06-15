package com.branacar.sale.model.person;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity @Table(name = "clients")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Client {

    @Id
    private UUID clientId;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(nullable = false)
    private String name;
}