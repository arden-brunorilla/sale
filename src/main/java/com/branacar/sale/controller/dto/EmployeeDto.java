package com.branacar.sale.controller.dto;

import com.branacar.sale.model.person.Employee;
import java.util.UUID;

public record EmployeeDto(
        UUID employeeId,
        String name,
        String role
) {
    public static EmployeeDto from(Employee e) {
        return new EmployeeDto(
                e.getEmployeeId(),
                e.getName(),
                e.getRole()
        );
    }
}