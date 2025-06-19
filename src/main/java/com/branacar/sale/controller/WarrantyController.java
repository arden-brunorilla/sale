package com.branacar.sale.controller;

import com.branacar.sale.controller.dto.NewWarrantyRequest;
import com.branacar.sale.controller.dto.WarrantyDto;
import com.branacar.sale.model.Warranty;
import com.branacar.sale.model.WarrantyStatus;
import com.branacar.sale.service.WarrantyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/warranties")
@RequiredArgsConstructor
public class WarrantyController {

    private final WarrantyService service;

    @PostMapping
    public ResponseEntity<WarrantyDto> create(@Valid @RequestBody NewWarrantyRequest req) {
        Warranty w = service.createWarranty(req);
        return ResponseEntity.ok(WarrantyDto.from(w));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<WarrantyDto> changeStatus(@PathVariable UUID id,
                                                    @RequestParam WarrantyStatus status) {
        Warranty w = service.updateStatus(id, status);
        return ResponseEntity.ok(WarrantyDto.from(w));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarrantyDto> get(@PathVariable UUID id) {
        Warranty w = service.getWarranty(id);
        return ResponseEntity.ok(WarrantyDto.from(w));
    }
}