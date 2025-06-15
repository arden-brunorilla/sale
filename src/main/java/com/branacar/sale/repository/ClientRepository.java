package com.branacar.sale.repository;

import com.branacar.sale.model.person.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> { }
