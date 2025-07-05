package com.branacar.sale.service;

import com.branacar.sale.controller.dto.NewWarrantyRequest;
import com.branacar.sale.model.Warranty;
import com.branacar.sale.model.WarrantyStatus;

import java.util.UUID;

public interface IWarrantyService {
    Warranty createWarranty(NewWarrantyRequest req);
    Warranty updateStatus(UUID id, WarrantyStatus newStatus);
    Warranty getWarranty(UUID id);
} 