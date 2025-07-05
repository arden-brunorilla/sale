package com.branacar.sale.client.dto;

import java.util.UUID;

public record StockDto(
        UUID stockId,
        String type,
        String address
) { } 