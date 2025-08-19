package com.bank.accountservice.dto;

import com.bank.accountservice.entity.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateAccountRequest(
    @NotBlank String ownerUsername,
    @NotNull AccountType type,
    @Pattern(regexp="^[A-Z0-9]{10,20}$", message="Account number must be 10-20 uppercase alphanumerics")
    String accountNumber
) {}
