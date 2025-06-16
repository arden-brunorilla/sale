package com.branacar.sale.client.dto;

import com.branacar.sale.model.MovementReason;   // re-usa el enum o crea uno igual
import java.util.UUID;

public record NewMovementRequest(
        UUID carId,
        UUID originId,
        UUID destinationId,
        MovementReason reason
) { }