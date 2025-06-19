package com.branacar.sale.repository;
import com.branacar.sale.model.MaintenanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface MaintenanceRepository extends JpaRepository<MaintenanceRecord, UUID> {
    List<MaintenanceRecord> findByCarId(UUID carId);
}