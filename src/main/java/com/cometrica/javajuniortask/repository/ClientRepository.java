package com.cometrica.javajuniortask.repository;

import com.cometrica.javajuniortask.model.Client;

import com.cometrica.javajuniortask.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    Optional<Client> findById(UUID uuid);
}
