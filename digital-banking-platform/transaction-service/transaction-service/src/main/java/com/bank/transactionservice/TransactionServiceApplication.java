package com.bank.transactionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;   

@SpringBootApplication
@EnableFeignClients(basePackages = "com.bank.transactionservice.client")  
public class TransactionServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransactionServiceApplication.class, args);
    }
}
