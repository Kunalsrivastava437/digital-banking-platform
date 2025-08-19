package com.bank.accountservice.dto;

import com.bank.accountservice.entity.AccountStatus;
import com.bank.accountservice.entity.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountDto(
    Long id,
    String accountNumber,
    String ownerUsername,
    AccountType type,
    AccountStatus status,
    BigDecimal balance,
    LocalDateTime createdAt
) {}
