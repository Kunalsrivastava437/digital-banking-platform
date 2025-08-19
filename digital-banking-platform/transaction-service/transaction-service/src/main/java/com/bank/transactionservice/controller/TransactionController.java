package com.bank.transactionservice.controller;

import com.bank.transactionservice.dto.TransactionDto;
import com.bank.transactionservice.dto.TransferRequest;
import com.bank.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

  private final TransactionService service;
  public TransactionController(TransactionService service) { this.service = service; }

  @PostMapping("/transfer")
  public ResponseEntity<TransactionDto> transfer(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
      @RequestHeader(value = "Idempotency-Key", required = false) String idemKey,
      @RequestBody @Valid TransferRequest request) {
    return ResponseEntity.ok(service.transfer(authorization, request, idemKey));
  }

  @GetMapping
  public ResponseEntity<List<TransactionDto>> byAccount(@RequestParam String accountNumber) {
    return ResponseEntity.ok(service.listByAccount(accountNumber));
  }
}
