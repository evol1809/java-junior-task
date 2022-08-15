package com.cometrica.javajuniortask.repository;

import com.cometrica.javajuniortask.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findAllByClientId(UUID iterable);
    @Query(value = "select sum(payment_value) from payment p where p.client_id=:client_id", nativeQuery = true)
    Optional<BigDecimal> getValueSum(@Param("client_id") UUID client);
}
