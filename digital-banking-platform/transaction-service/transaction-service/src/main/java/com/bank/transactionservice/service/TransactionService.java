package com.bank.transactionservice.service;

import com.bank.transactionservice.client.AccountFeignClient;
import com.bank.transactionservice.dto.TransactionDto;
import com.bank.transactionservice.dto.TransferRequest;
import com.bank.transactionservice.entity.Transaction;
import com.bank.transactionservice.entity.TransactionStatus;
import com.bank.transactionservice.entity.TransactionType;
import com.bank.transactionservice.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    private final TransactionRepository repo;
    private final AccountFeignClient accountFeign;

    public TransactionService(TransactionRepository repo, AccountFeignClient accountFeign) {
        this.repo = repo;
        this.accountFeign = accountFeign;
    }

    @Transactional
    public TransactionDto transfer(String bearerToken, TransferRequest req, String idempotencyKey) {
        // If client provided an Idempotency-Key, return previous result instead of duplicating
        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            var existing = repo.findByIdempotencyKey(idempotencyKey);
            if (existing.isPresent()) {
                return toDto(existing.get());
            }
        }

        // Create transaction PENDING
        var tx = Transaction.builder()
                .fromAccount(req.fromAccountNumber())
                .toAccount(req.toAccountNumber())
                .amount(req.amount())
                .type(TransactionType.TRANSFER)
                .status(TransactionStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .idempotencyKey(idempotencyKey)
                .build();
        tx = repo.save(tx);

        try {
            // Forward same Authorization header to account-service via Feign
            accountFeign.withdraw(bearerToken, req.fromAccountNumber(), Map.of("amount", req.amount()));
            accountFeign.deposit(bearerToken, req.toAccountNumber(), Map.of("amount", req.amount()));

            tx.setStatus(TransactionStatus.SUCCESS);
            return toDto(tx);
        } catch (Exception e) {
            tx.setStatus(TransactionStatus.FAILED);
            // Let it bubble up so controller returns 500; you can map custom errors if you want
            throw new RuntimeException("Transfer failed: " + e.getMessage(), e);
        }
    }

    public List<TransactionDto> listByAccount(String accountNumber) {
        return repo.findByFromAccountOrToAccountOrderByCreatedAtDesc(accountNumber, accountNumber)
                   .stream().map(this::toDto).toList();
    }

    private TransactionDto toDto(Transaction t) {
        return new TransactionDto(
                t.getId(),
                t.getFromAccount(),
                t.getToAccount(),
                t.getAmount(),
                t.getStatus(),
                t.getType(),
                t.getCreatedAt()
        );
    }
}
