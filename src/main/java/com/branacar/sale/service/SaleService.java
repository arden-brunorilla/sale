package com.branacar.sale.service;
import com.branacar.sale.client.CarFeignClient;
import com.branacar.sale.client.StockClient;
import com.branacar.sale.client.dto.CarDto;
import com.branacar.sale.client.dto.NewMovementRequest;
import com.branacar.sale.client.dto.StockDto;
import com.branacar.sale.client.dto.StockMovementDto;
import com.branacar.sale.controller.dto.NewSaleRequest;
import com.branacar.sale.controller.dto.NewWarrantyRequest;
import com.branacar.sale.controller.dto.SaleResponse;
import com.branacar.sale.controller.dto.SaleWithDeliveryResponse;
import com.branacar.sale.model.*;
import com.branacar.sale.model.SaleStatus;
import com.branacar.sale.model.person.Client;
import com.branacar.sale.model.person.Employee;
import com.branacar.sale.repository.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaleService implements ISaleService {

    private final SaleRepository saleRepo;
    private final ClientRepository clientRepo;
    private final EmployeeRepository employeeRepo;
    private final CarFeignClient carClient;
    private final StockClient stockClient;
    private final WarrantyService warrantyService;


    @Transactional
    public SaleResponse createSale(NewSaleRequest req) {


        Client client = clientRepo.findById(req.clientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        Employee employee = employeeRepo.findById(req.employeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));


        CarDto car = carClient.getCar(req.carId());
        if (!"IN_STOCK".equals(car.status())) {
            throw new IllegalStateException("Car is not available for sale");
        }


        BigDecimal subtotal = req.agreedPrice();
        BigDecimal discounts = req.lineDiscount();
        BigDecimal total = subtotal.subtract(discounts);


        SaleItem item = SaleItem.builder()
                .itemId(UUID.randomUUID())
                .carId(req.carId())
                .agreedPrice(subtotal)
                .lineDiscount(discounts)
                .originStockId(req.originStockId())
                .lineTaxes(BigDecimal.ZERO)
                .lineSubtotal(total)
                .promisedDeliveryDate(req.promisedDeliveryDate())
                .build();

        Sale sale = Sale.builder()
                .saleId(UUID.randomUUID())
                .date(LocalDate.now())
                .status(SaleStatus.OPEN)
                .subtotal(subtotal)
                .discounts(discounts)
                .total(total)
                .client(client)
                .employee(employee)
                .items(List.of(item))
                .build();

        saleRepo.save(sale);
        return SaleResponse.from(sale);
    }

    public Sale getSale(UUID id) {
        return saleRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Sale not found"));
    }

    @Transactional
    public Sale closeSale(UUID saleId, UUID destinationStockId) {
        System.out.println("Closing sale " + saleId);
        Sale sale = saleRepo.findById(saleId)
                .orElseThrow(() -> new NotFoundException("Sale not found"));

        if (sale.getStatus() != SaleStatus.OPEN) {
            throw new IllegalStateException("Sale already closed or cancelled");
        }

        sale.getItems().forEach(item -> {
            stockClient.move(new NewMovementRequest(
                    item.getCarId(),
                    item.getOriginStockId(),
                    destinationStockId,
                    MovementReason.SALE));
        });

        sale.setStatus(SaleStatus.CLOSED);
        

        SaleItem firstItem = sale.getItems().get(0);
        warrantyService.createWarranty(new NewWarrantyRequest(
                saleId,
                firstItem.getCarId(),
                sale.getClient().getClientId(),
                LocalDate.now().plusYears(1),
                "Garantía estándar 12 meses"
        ));
        return sale;
    }

    @Transactional
    public SaleWithDeliveryResponse closeSaleWithDelivery(UUID saleId, UUID destinationStockId) {
        System.out.println("Closing sale " + saleId);
        Sale sale = saleRepo.findById(saleId)
                .orElseThrow(() -> new NotFoundException("Sale not found"));

        if (sale.getStatus() != SaleStatus.OPEN) {
            throw new IllegalStateException("Sale already closed or cancelled");
        }

        SaleItem firstItem = sale.getItems().get(0);
        UUID carId = firstItem.getCarId();
        UUID originStockId = firstItem.getOriginStockId();

        try {
            StockDto currentStock = stockClient.getCarLocation(carId);
            
            if ("LOCAL".equals(currentStock.type())) {
                return processImmediateSale(sale, carId, originStockId, destinationStockId);
            } else if ("CENTRAL".equals(currentStock.type())) {
                return processCentralStockSale(sale, carId, currentStock.stockId(), destinationStockId);
            } else {
                throw new IllegalStateException("Unknown stock type: " + currentStock.type());
            }
            
        } catch (Exception e) {
            throw new IllegalStateException("Error determining car location: " + e.getMessage());
        }
    }

    private SaleWithDeliveryResponse processImmediateSale(Sale sale, UUID carId, UUID originStockId, UUID destinationStockId) {
        stockClient.move(new NewMovementRequest(
                carId,
                originStockId,
                destinationStockId,
                MovementReason.SALE));

        sale.setStatus(SaleStatus.CLOSED);
        saleRepo.save(sale);

        // Crear garantía
        warrantyService.createWarranty(new NewWarrantyRequest(
                sale.getSaleId(),
                carId,
                sale.getClient().getClientId(),
                LocalDate.now().plusYears(1),
                "Garantía estándar 12 meses"
        ));

        return SaleWithDeliveryResponse.immediateDelivery(sale);
    }

    private SaleWithDeliveryResponse processCentralStockSale(Sale sale, UUID carId, UUID centralStockId, UUID localStockId) {
        // Reservar auto del stock central
        stockClient.reserveCar(carId, centralStockId, localStockId);

        // Calcular fecha de delivery (entre 2-7 días)
        Random random = new Random();
        int deliveryDays = random.nextInt(6) + 2; // 2-7 días
        LocalDate deliveryDate = LocalDate.now().plusDays(deliveryDays);

        // Marcar venta como reservada
        sale.setStatus(SaleStatus.RESERVED);
        saleRepo.save(sale);

        return SaleWithDeliveryResponse.scheduledDelivery(sale, deliveryDate, deliveryDays);
    }

    public CarDto getCarFallback(UUID carId, Exception e) {
        log.error("Fallback: Error getting car {} - {}", carId, e.getMessage());
        throw new IllegalStateException("Car service unavailable - " + e.getMessage());
    }

    public StockDto getCarLocationFallback(UUID carId, Exception e) {
        log.error("Fallback: Error getting car location {} - {}", carId, e.getMessage());
        throw new IllegalStateException("Stock service unavailable - " + e.getMessage());
    }

    public StockMovementDto moveCarFallback(NewMovementRequest req, Exception e) {
        log.error("Fallback: Error moving car {} - {}", req.carId(), e.getMessage());
        throw new IllegalStateException("Stock service unavailable - " + e.getMessage());
    }

    public StockMovementDto reserveCarFallback(UUID carId, UUID centralStockId, UUID localStockId, Exception e) {
        log.error("Fallback: Error reserving car {} - {}", carId, e.getMessage());
        throw new IllegalStateException("Stock service unavailable - " + e.getMessage());
    }
}
