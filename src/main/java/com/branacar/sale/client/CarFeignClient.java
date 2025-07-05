package com.branacar.sale.client;

import com.branacar.sale.client.dto.CarDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "car-service")
public interface CarFeignClient {

    @CircuitBreaker(name = "car-service")
    @GetMapping("/cars/{id}")
    CarDto getCar(@PathVariable("id") UUID id);

    @GetMapping("/cars")
    Object findAll();

    @GetMapping("/cars/by-stock/{stockId}")
    Object findByStock(@PathVariable UUID stockId);
}