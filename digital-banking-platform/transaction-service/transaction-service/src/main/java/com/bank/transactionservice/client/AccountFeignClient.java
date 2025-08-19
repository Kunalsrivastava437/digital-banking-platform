package com.bank.transactionservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;

@FeignClient(name = "account-service", path = "/api/accounts")
public interface AccountFeignClient {

  @PostMapping(value="/{accountNumber}/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE)
  void withdraw(@RequestHeader("Authorization") String bearer,
                @PathVariable String accountNumber,
                @RequestBody Map<String, Object> payload);

  @PostMapping(value="/{accountNumber}/deposit", consumes = MediaType.APPLICATION_JSON_VALUE)
  void deposit(@RequestHeader("Authorization") String bearer,
               @PathVariable String accountNumber,
               @RequestBody Map<String, Object> payload);
}
