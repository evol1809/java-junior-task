package com.cometrica.javajuniortask.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class Debt {
    @Id
    private UUID id;

    @Column(name="debt_value")
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
