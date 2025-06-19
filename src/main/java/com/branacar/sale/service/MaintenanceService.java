package com.branacar.sale.service;

import com.branacar.sale.controller.dto.NewMaintenanceRequest;
import com.branacar.sale.model.MaintenanceRecord;
import com.branacar.sale.model.MaintenanceStatus;
import com.branacar.sale.repository.MaintenanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepo;

    @Transactional
    public MaintenanceRecord schedule(NewMaintenanceRequest req) {
        MaintenanceRecord rec = MaintenanceRecord.builder()
                .maintenanceId(UUID.randomUUID())
                .carId(req.carId())
                .clientId(req.clientId())
                .warrantyId(req.warrantyId())
                .scheduledDate(req.scheduledDate())
                .status(MaintenanceStatus.SCHEDULED)
                .description(req.description())
                .cost(null)
                .build();
        return maintenanceRepo.save(rec);
    }

    public List<MaintenanceRecord> findByCar(UUID carId) {
        return maintenanceRepo.findByCarId(carId);
    }
}