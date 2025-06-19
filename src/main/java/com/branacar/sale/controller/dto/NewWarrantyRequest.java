package com.branacar.sale.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record NewWarrantyRequest(
        @NotNull UUID saleId,
        @NotNull LocalDate endDate,
        @Size(max = 500) String description
) { }