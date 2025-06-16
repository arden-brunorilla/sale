package com.branacar.sale.controller.dto;

import com.branacar.sale.model.person.Client;
import java.util.UUID;

public record ClientDto(UUID clientId, String dni, String name) {
    public static ClientDto from(Client c) {
        return new ClientDto(c.getClientId(), c.getDni(), c.getName());
    }
}