package com.branacar.sale.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "maintenance-service")
public interface MaintenanceClient {
    
    @PostMapping("/api/maintenance")
    MaintenanceResponseDto createMaintenance(MaintenanceRequestDto request);
    
    @GetMapping("/api/maintenance/car/{carId}")
    List<MaintenanceResponseDto> getMaintenanceByCarId(@PathVariable UUID carId);
    
    @PostMapping("/api/maintenance/{maintenanceId}/start")
    MaintenanceResponseDto startMaintenance(@PathVariable UUID maintenanceId);
    
    @PostMapping("/api/maintenance/{maintenanceId}/complete")
    MaintenanceResponseDto completeMaintenance(
        @PathVariable UUID maintenanceId,
        @RequestParam(required = false) String finalNotes,
        @RequestParam(required = false) Double finalCost);
    
    public record MaintenanceRequestDto(
        UUID carId,
        String type,
        String description,
        String scheduledDate,
        String technicianName,
        String serviceNotes,
        Double estimatedCost
    ) {}
    
    public record MaintenanceResponseDto(
        UUID maintenanceId,
        UUID carId,
        String type,
        String status,
        String description,
        String scheduledDate,
        String startDate,
        String completionDate,
        Double cost,
        String technicianName,
        String serviceNotes,
        String createdAt
    ) {}
} 