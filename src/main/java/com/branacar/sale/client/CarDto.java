package com.branacar.sale.client;

import java.math.BigDecimal;
import java.util.UUID;

public record CarDto(
        UUID carId,
        String vin,
        String status,
        BigDecimal listPrice
) { }
