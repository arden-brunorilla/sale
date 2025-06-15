package com.branacar.sale;

import com.branacar.sale.model.person.Client;
import com.branacar.sale.model.person.Employee;
import com.branacar.sale.repository.ClientRepository;
import com.branacar.sale.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.UUID;




@SpringBootApplication
public class SaleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaleServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner seed(ClientRepository cRepo, EmployeeRepository eRepo) {
        return args -> {
            if (cRepo.count() == 0) {
                cRepo.save(Client.builder()
                        .clientId(UUID.fromString("33333333-aaaa-bbbb-cccc-111111111111"))
                        .dni("12345678")
                        .name("Juan Pérez")
                        .build());
            }
            if (eRepo.count() == 0) {
                eRepo.save(Employee.builder()
                        .employeeId(UUID.fromString("44444444-aaaa-bbbb-cccc-222222222222"))
                        .name("Ana López")
                        .role("Sales Rep")
                        .build());
            }
        };
    }

}
