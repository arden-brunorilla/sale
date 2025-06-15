package com.branacar.sale.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity @Table(name = "sale_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SaleItem {

    @Id
    private UUID itemId;

    private UUID carId;                    // referencia al Car
    private BigDecimal agreedPrice;
    private BigDecimal lineDiscount;
    private BigDecimal lineTaxes;
    private BigDecimal lineSubtotal;
    private LocalDate promisedDeliveryDate;
}
