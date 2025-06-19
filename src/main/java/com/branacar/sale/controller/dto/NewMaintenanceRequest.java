package com.branacar.sale.controller.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record NewMaintenanceRequest(
        @NotNull UUID carId,
        @NotNull LocalDate scheduledDate,
        String description
) {}
