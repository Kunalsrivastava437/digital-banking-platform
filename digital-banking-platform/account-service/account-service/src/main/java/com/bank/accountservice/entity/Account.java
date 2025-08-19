package com.bank.accountservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name="accounts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Account {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, unique=true)
  private String accountNumber;

  @Column(nullable=false)
  private String ownerUsername; // from user-service (we’ll validate via Feign later)

  @Enumerated(EnumType.STRING)
  @Column(nullable=false)
  private AccountType type;

  @Enumerated(EnumType.STRING)
  @Column(nullable=false)
  private AccountStatus status;

  @Column(nullable=false, precision=18, scale=2)
  private BigDecimal balance;

  @Column(nullable=false)
  private LocalDateTime createdAt;
}
