package com.bank.accountservice.service;

import com.bank.accountservice.dto.AccountDto;
import com.bank.accountservice.dto.CreateAccountRequest;
import com.bank.accountservice.entity.Account;
import com.bank.accountservice.entity.AccountStatus;
import com.bank.accountservice.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

  private final AccountRepository repo;

  public AccountService(AccountRepository repo) {
    this.repo = repo;
  }

  public AccountDto create(CreateAccountRequest req) {
    if (repo.findByAccountNumber(req.accountNumber()).isPresent()) {
      throw new IllegalArgumentException("Account number already exists");
    }
    Account acc = Account.builder()
        .accountNumber(req.accountNumber())
        .ownerUsername(req.ownerUsername())
        .type(req.type())
        .status(AccountStatus.ACTIVE)
        .balance(new BigDecimal("0.00"))
        .createdAt(LocalDateTime.now())
        .build();
    var saved = repo.save(acc);
    return toDto(saved);
  }

  public AccountDto getByAccountNumber(String accountNumber) {
    return toDto(repo.findByAccountNumber(accountNumber).orElseThrow());
  }

  public List<AccountDto> getByOwner(String ownerUsername) {
    return repo.findByOwnerUsername(ownerUsername).stream().map(this::toDto).toList();
  }

  @Transactional
  public AccountDto deposit(String accountNumber, BigDecimal amount) {
    var acc = repo.findByAccountNumber(accountNumber).orElseThrow();
    if (acc.getStatus() != AccountStatus.ACTIVE) throw new IllegalStateException("Account not active");
    acc.setBalance(acc.getBalance().add(amount));
    return toDto(acc);
  }

  @Transactional
  public AccountDto withdraw(String accountNumber, BigDecimal amount) {
    var acc = repo.findByAccountNumber(accountNumber).orElseThrow();
    if (acc.getStatus() != AccountStatus.ACTIVE) throw new IllegalStateException("Account not active");
    if (acc.getBalance().compareTo(amount) < 0) throw new IllegalArgumentException("Insufficient funds");
    acc.setBalance(acc.getBalance().subtract(amount));
    return toDto(acc);
  }

  private AccountDto toDto(Account a) {
    return new AccountDto(a.getId(), a.getAccountNumber(), a.getOwnerUsername(), a.getType(),
        a.getStatus(), a.getBalance(), a.getCreatedAt());
  }
}
