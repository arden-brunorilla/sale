package com.branacar.sale.service;
import com.branacar.sale.client.CarClient;
import com.branacar.sale.client.StockClient;
import com.branacar.sale.client.dto.CarDto;
import com.branacar.sale.client.dto.NewMovementRequest;
import com.branacar.sale.controller.dto.NewSaleRequest;
import com.branacar.sale.controller.dto.SaleResponse;
import com.branacar.sale.model.*;
import com.branacar.sale.model.SaleStatus;
import com.branacar.sale.model.person.Client;
import com.branacar.sale.model.person.Employee;
import com.branacar.sale.repository.*;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepo;
    private final ClientRepository clientRepo;
    private final EmployeeRepository employeeRepo;
    private final CarClient carClient;
    private final StockClient stockClient;


    @Transactional
    public SaleResponse createSale(NewSaleRequest req) {


        Client client = clientRepo.findById(req.clientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        Employee employee = employeeRepo.findById(req.employeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));


        CarDto car = carClient.findById(req.carId());
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
        return sale;
    }
}
