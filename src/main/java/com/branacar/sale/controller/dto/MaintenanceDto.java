package com.branacar.sale.controller.dto;

import com.branacar.sale.model.MaintenanceRecord;
import com.branacar.sale.model.MaintenanceStatus;
import java.time.LocalDate;
import java.util.UUID;

public record MaintenanceDto(
        UUID maintenanceId,
        UUID carId,
        LocalDate scheduledDate,
        MaintenanceStatus status,
        String description,
        Double cost
) {
    public static MaintenanceDto from(MaintenanceRecord m) {
        return new MaintenanceDto(
                m.getMaintenanceId(),
                m.getCarId(),
                m.getScheduledDate(),
                m.getStatus(),
                m.getDescription(),
                m.getCost()
        );
    }
}