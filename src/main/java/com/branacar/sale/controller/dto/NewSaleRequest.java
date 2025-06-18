package com.branacar.sale.controller.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record NewSaleRequest(
        @NotNull UUID carId,
        @NotNull UUID clientId,
        @NotNull UUID employeeId,
        @DecimalMin("0.0") BigDecimal agreedPrice,
        @DecimalMin("0.0") BigDecimal lineDiscount,
        @NotNull LocalDate promisedDeliveryDate,
        @NotNull UUID originStockId
) { }