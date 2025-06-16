package com.branacar.sale.client;

import com.branacar.sale.client.dto.NewMovementRequest;
import com.branacar.sale.client.dto.StockMovementDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "stock-service")
public interface StockClient {

    @PostMapping("/stocks/movements")
    StockMovementDto move(@RequestBody NewMovementRequest req);
}