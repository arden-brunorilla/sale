package com.branacar.sale.controller.dto;

import com.branacar.sale.model.Sale;
import com.branacar.sale.model.SaleStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record SaleResponse(
        UUID saleId,
        LocalDate date,
        SaleStatus status,
        BigDecimal total,
        UUID clientId,
        UUID employeeId
) {
    public static SaleResponse from(Sale sale) {
        return new SaleResponse(
                sale.getSaleId(),
                sale.getDate(),
                sale.getStatus(),
                sale.getTotal(),
                sale.getClient().getClientId(),
                sale.getEmployee().getEmployeeId()
        );
    }
}