package com.branacar.sale.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "warranties")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Warranty {

    @Id
    private UUID warrantyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;


    @Column(nullable = false)
    private UUID carId;

    @Column(nullable = false)
    private UUID clientId;

    @Enumerated(EnumType.STRING)
    private WarrantyStatus status;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(length = 500)
    private String description;
}