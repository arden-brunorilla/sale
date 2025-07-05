package com.branacar.sale.service;

import com.branacar.sale.controller.dto.*;
import com.branacar.sale.model.Sale;

import java.util.UUID;

public interface ISaleService {
    SaleResponse createSale(NewSaleRequest req);
    Sale getSale(UUID id);
    Sale closeSale(UUID saleId, UUID destinationStockId);
    SaleWithDeliveryResponse closeSaleWithDelivery(UUID saleId, UUID destinationStockId);
}