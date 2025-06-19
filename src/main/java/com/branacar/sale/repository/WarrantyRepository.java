package com.branacar.sale.repository;

import com.branacar.sale.model.Warranty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WarrantyRepository extends JpaRepository<Warranty, UUID> {
}