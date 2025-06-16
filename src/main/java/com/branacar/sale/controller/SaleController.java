package com.branacar.sale.controller;

import com.branacar.sale.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService service;

    @PostMapping
    public ResponseEntity<SaleResponse> createSale(@Valid @RequestBody NewSaleRequest body) {
        return ResponseEntity.ok( service.createSale(body) );
    }
}
