package com.branacar.sale.service;

import com.branacar.sale.controller.dto.NewWarrantyRequest;
import com.branacar.sale.model.Sale;
import com.branacar.sale.model.Warranty;
import com.branacar.sale.model.WarrantyStatus;
import com.branacar.sale.repository.SaleRepository;
import com.branacar.sale.repository.WarrantyRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WarrantyService {

    private final WarrantyRepository warrantyRepo;
    private final SaleRepository saleRepo;

    @Transactional
    public Warranty createWarranty(NewWarrantyRequest req) {
        Sale sale = saleRepo.findById(req.saleId())
                .orElseThrow(() -> new NotFoundException("Sale not found"));

        Warranty w = Warranty.builder()
                .warrantyId(UUID.randomUUID())
                .sale(sale)
                .carId(req.carId())
                .clientId(req.clientId())
                .status(WarrantyStatus.ACTIVE)
                .startDate(LocalDate.now())
                .endDate(req.endDate())
                .description(req.description())
                .build();

        return warrantyRepo.save(w);
    }

    @Transactional
    public Warranty updateStatus(UUID id, WarrantyStatus newStatus) {
        Warranty w = warrantyRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Warranty not found"));
        w.setStatus(newStatus);
        return w;
    }

    @Transactional(readOnly = true)
    public Warranty getWarranty(UUID id) {
        return warrantyRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Warranty not found"));
    }
}
