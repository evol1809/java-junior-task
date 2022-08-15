package com.cometrica.javajuniortask.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
public class Payment {
    @Id
    private UUID id;

    @Column(name="payment_value")
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
