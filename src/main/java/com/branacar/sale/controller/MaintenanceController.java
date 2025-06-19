package com.branacar.sale.controller;
import com.branacar.sale.controller.dto.MaintenanceDto;
import com.branacar.sale.controller.dto.NewMaintenanceRequest;
import com.branacar.sale.model.MaintenanceRecord;
import com.branacar.sale.service.MaintenanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<MaintenanceDto> schedule(@Valid @RequestBody NewMaintenanceRequest req) {
        MaintenanceRecord rec = maintenanceService.schedule(req);
        return ResponseEntity.ok(MaintenanceDto.from(rec));
    }

    @GetMapping("/by-car/{carId}")
    public List<MaintenanceDto> byCar(@PathVariable UUID carId) {
        return maintenanceService.findByCar(carId).stream()
                .map(MaintenanceDto::from)
                .toList();
    }
}