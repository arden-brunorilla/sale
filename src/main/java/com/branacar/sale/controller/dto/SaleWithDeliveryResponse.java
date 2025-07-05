package com.branacar.sale.controller.dto;

import com.branacar.sale.model.Sale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleWithDeliveryResponse {
    private UUID saleId;
    private String status;
    private String deliveryType; // "IMMEDIATE" or "SCHEDULED"
    private LocalDate estimatedDeliveryDate;
    private int deliveryDays;
    private String message;
    
    public static SaleWithDeliveryResponse immediateDelivery(Sale sale) {
        return new SaleWithDeliveryResponse(
            sale.getSaleId(),
            "CLOSED",
            "IMMEDIATE",
            LocalDate.now(),
            0,
            "Venta cerrada - Auto disponible inmediatamente"
        );
    }
    
    public static SaleWithDeliveryResponse scheduledDelivery(Sale sale, LocalDate deliveryDate, int days) {
        return new SaleWithDeliveryResponse(
            sale.getSaleId(),
            "RESERVED",
            "SCHEDULED",
            deliveryDate,
            days,
            "Venta reservada - Auto será transferido desde stock central"
        );
    }
} 