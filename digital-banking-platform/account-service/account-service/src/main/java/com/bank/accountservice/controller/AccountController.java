package com.bank.accountservice.controller;

import com.bank.accountservice.dto.AccountDto;
import com.bank.accountservice.dto.CreateAccountRequest;
import com.bank.accountservice.dto.MoneyRequest;
import com.bank.accountservice.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

  private final AccountService service;

  public AccountController(AccountService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<AccountDto> create(@RequestBody @Valid CreateAccountRequest req) {
    return ResponseEntity.ok(service.create(req));
  }

  @GetMapping("/{accountNumber}")
  public ResponseEntity<AccountDto> byAccountNumber(@PathVariable String accountNumber) {
    return ResponseEntity.ok(service.getByAccountNumber(accountNumber));
  }

  @GetMapping
  public ResponseEntity<List<AccountDto>> byOwner(@RequestParam String ownerUsername) {
    return ResponseEntity.ok(service.getByOwner(ownerUsername));
  }

  @PostMapping("/{accountNumber}/deposit")
  public ResponseEntity<AccountDto> deposit(@PathVariable String accountNumber,
                                            @RequestBody @Valid MoneyRequest req) {
    return ResponseEntity.ok(service.deposit(accountNumber, req.amount()));
  }

  @PostMapping("/{accountNumber}/withdraw")
  public ResponseEntity<AccountDto> withdraw(@PathVariable String accountNumber,
                                             @RequestBody @Valid MoneyRequest req) {
    return ResponseEntity.ok(service.withdraw(accountNumber, req.amount()));
  }
}
