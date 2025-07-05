package com.branacar.sale.controller;

import com.branacar.sale.client.MaintenanceClient;
import com.branacar.sale.controller.dto.NewSaleRequest;
import com.branacar.sale.controller.dto.SaleDto;
import com.branacar.sale.controller.dto.SaleResponse;
import com.branacar.sale.controller.dto.SaleWithDeliveryResponse;
import com.branacar.sale.service.ISaleService;
import com.branacar.sale.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final ISaleService service;
    private final MaintenanceClient maintenanceClient;

    @GetMapping("/{id}")
    public ResponseEntity<SaleDto> getSale(@PathVariable UUID id) {
        return ResponseEntity.ok(
                SaleDto.from(service.getSale(id))
        );
    }

    @PostMapping
    public ResponseEntity<SaleResponse> createSale(@Valid @RequestBody NewSaleRequest body) {
        return ResponseEntity.ok( service.createSale(body) );
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<SaleDto> closeSale(@PathVariable UUID id,
                                             @RequestParam UUID destinationStockId) {
        return ResponseEntity.ok(
                SaleDto.from( service.closeSale(id, destinationStockId) ));
    }

    @PatchMapping("/{id}/close-with-delivery")
    public ResponseEntity<SaleWithDeliveryResponse> closeSaleWithDelivery(@PathVariable UUID id,
                                             @RequestParam UUID destinationStockId) {
        return ResponseEntity.ok( service.closeSaleWithDelivery(id, destinationStockId) );
    }

    // Maintenance endpoints - delegating to maintenance-service
    @PostMapping("/{saleId}/maintenance")
    public ResponseEntity<MaintenanceClient.MaintenanceResponseDto> scheduleMaintenance(
            @PathVariable UUID saleId,
            @RequestBody MaintenanceClient.MaintenanceRequestDto request) {
        return ResponseEntity.ok(maintenanceClient.createMaintenance(request));
    }

    @GetMapping("/{saleId}/maintenance")
    public ResponseEntity<List<MaintenanceClient.MaintenanceResponseDto>> getMaintenanceByCar(
            @PathVariable UUID saleId) {
        // Get car ID from sale and then get maintenance
        // We need to get the car ID from the sale items
        // For now, we'll require the car ID as a parameter
        return ResponseEntity.ok(maintenanceClient.getMaintenanceByCarId(null));
    }

    @GetMapping("/{saleId}/maintenance/car/{carId}")
    public ResponseEntity<List<MaintenanceClient.MaintenanceResponseDto>> getMaintenanceByCarId(
            @PathVariable UUID saleId,
            @PathVariable UUID carId) {
        return ResponseEntity.ok(maintenanceClient.getMaintenanceByCarId(carId));
    }

    @PostMapping("/{saleId}/maintenance/{maintenanceId}/start")
    public ResponseEntity<MaintenanceClient.MaintenanceResponseDto> startMaintenance(
            @PathVariable UUID saleId,
            @PathVariable UUID maintenanceId) {
        return ResponseEntity.ok(maintenanceClient.startMaintenance(maintenanceId));
    }

    @PostMapping("/{saleId}/maintenance/{maintenanceId}/complete")
    public ResponseEntity<MaintenanceClient.MaintenanceResponseDto> completeMaintenance(
            @PathVariable UUID saleId,
            @PathVariable UUID maintenanceId,
            @RequestParam(required = false) String finalNotes,
            @RequestParam(required = false) Double finalCost) {
        return ResponseEntity.ok(maintenanceClient.completeMaintenance(maintenanceId, finalNotes, finalCost));
    }
}
