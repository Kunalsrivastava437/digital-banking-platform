package com.bank.transactionservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name="transactions", indexes = {
  @Index(name="idx_tx_from", columnList = "fromAccount"),
  @Index(name="idx_tx_to", columnList = "toAccount"),
  @Index(name="idx_idem_key", columnList = "idempotencyKey", unique = true)
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Transaction {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false)
  private String fromAccount;

  @Column(nullable=false)
  private String toAccount;

  @Column(nullable=false, precision=18, scale=2)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable=false)
  private TransactionStatus status;

  @Enumerated(EnumType.STRING)
  @Column(nullable=false)
  private TransactionType type; // TRANSFER

  @Column(nullable=false)
  private LocalDateTime createdAt;

  @Column(nullable=true, unique = true)
  private String idempotencyKey; // optional header to prevent duplicates
}
