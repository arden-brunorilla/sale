package com.branacar.sale.controller.dto;

import com.branacar.sale.model.Sale;
import com.branacar.sale.model.SaleStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record SaleDto(
        UUID saleId,
        LocalDate date,
        SaleStatus status,
        BigDecimal subtotal,
        BigDecimal discounts,
        BigDecimal total,
        UUID clientId,
        UUID employeeId
) {
    public static SaleDto from(Sale s) {
        return new SaleDto(
                s.getSaleId(),
                s.getDate(),
                s.getStatus(),
                s.getSubtotal(),
                s.getDiscounts(),
                s.getTotal(),
                s.getClient().getClientId(),
                s.getEmployee().getEmployeeId()
        );
    }
}