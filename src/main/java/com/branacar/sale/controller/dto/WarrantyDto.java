package com.branacar.sale.controller.dto;
import com.branacar.sale.model.Warranty;
import com.branacar.sale.model.WarrantyStatus;

import java.time.LocalDate;
import java.util.UUID;

public record WarrantyDto(
        UUID warrantyId,
        UUID saleId,
        WarrantyStatus status,
        LocalDate startDate,
        LocalDate endDate,
        String description
) {
    public static WarrantyDto from(Warranty w) {
        return new WarrantyDto(
                w.getWarrantyId(),
                w.getSale().getSaleId(),
                w.getStatus(),
                w.getStartDate(),
                w.getEndDate(),
                w.getDescription()
        );
    }
}