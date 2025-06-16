package com.branacar.sale.client;

import com.branacar.sale.client.dto.CarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarClient {

    private final RestTemplate restTemplate;
    private static final String CAR_SERVICE = "car-service";   // spring.application.name del otro MS

    public CarDto findById(UUID id) {
        return restTemplate.getForObject(
                "http://" + CAR_SERVICE + "/cars/{id}",
                CarDto.class,
                id
        );
    }
}
