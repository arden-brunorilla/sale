package com.branacar.sale.repository;

import com.branacar.sale.model.person.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> { }