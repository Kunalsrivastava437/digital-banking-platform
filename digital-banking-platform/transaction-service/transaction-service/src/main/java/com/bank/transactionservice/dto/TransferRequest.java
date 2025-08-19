package com.bank.transactionservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record TransferRequest(
  @NotBlank String fromAccountNumber,
  @NotBlank String toAccountNumber,
  @DecimalMin(value="0.01") @Digits(integer=16, fraction=2) BigDecimal amount
) {}
