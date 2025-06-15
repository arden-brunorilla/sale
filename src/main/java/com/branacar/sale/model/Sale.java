package com.branacar.sale.model;

import com.branacar.sale.model.SaleStatus;
import com.branacar.sale.model.person.Client;
import com.branacar.sale.model.person.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sales")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Sale {

    @Id
    private UUID saleId;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private SaleStatus status;

    private BigDecimal subtotal;
    private BigDecimal discounts;
    private BigDecimal total;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sale_id")
    private List<SaleItem> items;
}