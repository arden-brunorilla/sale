package com.branacar.sale.model;

import com.branacar.sale.model.SaleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity @Table(name = "sales")
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

    /* --- Relaciones --- */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sale_id")          // FK en sale_items
    private List<SaleItem> items;
}