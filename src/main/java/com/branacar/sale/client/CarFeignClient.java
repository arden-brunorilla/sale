package com.branacar.sale.client;

import com.branacar.sale.client.dto.CarDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "car-service")
public interface CarFeignClient {

    @GetMapping("/cars/{id}")
    CarDto findById(@PathVariable UUID id);

    @GetMapping("/cars")
    Object findAll();

    @GetMapping("/cars/by-stock/{stockId}")
    Object findByStock(@PathVariable UUID stockId);
}