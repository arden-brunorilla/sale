package com.branacar.sale.controller;

import com.branacar.sale.controller.dto.SaleDto;
import com.branacar.sale.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService service;

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
}
