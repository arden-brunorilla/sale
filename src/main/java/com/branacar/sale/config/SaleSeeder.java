package com.branacar.sale.config;

import com.branacar.sale.model.person.Client;
import com.branacar.sale.model.person.Employee;
import com.branacar.sale.repository.ClientRepository;
import com.branacar.sale.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class SaleSeeder {

    private final ClientRepository   clientRepo;
    private final EmployeeRepository employeeRepo;
    private final ObjectMapper       mapper;

    @Bean
    CommandLineRunner seedPeople() {
        return args -> {
            if (clientRepo.count() > 0) return;

            try (InputStream in = getClass().getResourceAsStream("/seed/seed-data.json")) {
                JsonNode root = mapper.readTree(in);

                // Clients
                for (JsonNode c : root.path("clients")) {
                    clientRepo.save(
                            Client.builder()
                                    .clientId(UUID.fromString(c.get("clientId").asText()))
                                    .dni(c.get("dni").asText())
                                    .name(c.get("name").asText())
                                    .build());
                }

                // Employees
                for (JsonNode e : root.path("employees")) {
                    employeeRepo.save(
                            Employee.builder()
                                    .employeeId(UUID.fromString(e.get("employeeId").asText()))
                                    .name(e.get("name").asText())
                                    .role(e.get("role").asText())
                                    .build());
                }
                System.out.println("✅ People seed loaded");
            }
        };
    }
}
