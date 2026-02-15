package com.levelvini.picpay_simplificado.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name = "sender-id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiver-id")
    private User receiver;
    private LocalDateTime timestamp;
}
