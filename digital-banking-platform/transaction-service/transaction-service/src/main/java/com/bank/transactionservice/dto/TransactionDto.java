package com.bank.transactionservice.dto;

import com.bank.transactionservice.entity.TransactionStatus;
import com.bank.transactionservice.entity.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto(
  Long id,
  String fromAccount,
  String toAccount,
  BigDecimal amount,
  TransactionStatus status,
  TransactionType type,
  LocalDateTime createdAt
) {}
