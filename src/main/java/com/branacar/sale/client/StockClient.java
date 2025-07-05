package com.branacar.sale.client;

import com.branacar.sale.client.dto.NewMovementRequest;
import com.branacar.sale.client.dto.StockDto;
import com.branacar.sale.client.dto.StockMovementDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "stock-service")
public interface StockClient {

    @CircuitBreaker(name = "stock-service")
    @PostMapping("/stocks/movements")
    StockMovementDto move(@RequestBody NewMovementRequest req);

    @CircuitBreaker(name = "stock-service")
    @PostMapping("/stocks/reserve")
    StockMovementDto reserveCar(
            @RequestParam("carId") UUID carId,
            @RequestParam("centralStockId") UUID centralStockId,
            @RequestParam("localStockId") UUID localStockId);

    @CircuitBreaker(name = "stock-service")
    @GetMapping("/stocks/car/{carId}/location")
    StockDto getCarLocation(@PathVariable("carId") UUID carId);
}