package com.branacar.sale.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "maintenance_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceRecord {
    @Id
    private UUID maintenanceId;

    @Column(nullable = false)
    private UUID carId;


    @Column(nullable = false)
    private UUID clientId;


    @Column
    private UUID warrantyId;

    @Column(nullable = false)
    private LocalDate scheduledDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceStatus status;
    private String description;
    private Double cost;
}