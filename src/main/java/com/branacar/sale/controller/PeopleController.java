package com.branacar.sale.controller;

import com.branacar.sale.controller.dto.ClientDto;
import com.branacar.sale.controller.dto.EmployeeDto;
import com.branacar.sale.repository.ClientRepository;
import com.branacar.sale.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PeopleController {

    private final ClientRepository clientRepo;
    private final EmployeeRepository employeeRepo;

    @GetMapping("/clients")
    public List<ClientDto> listClients() {
        return clientRepo.findAll().stream().map(ClientDto::from).toList();
    }

    @GetMapping("/employees")
    public List<EmployeeDto> listEmployees() {
        return employeeRepo.findAll().stream().map(EmployeeDto::from).toList();
    }
}
