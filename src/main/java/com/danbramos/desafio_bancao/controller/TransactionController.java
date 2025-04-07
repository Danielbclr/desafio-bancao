package com.danbramos.desafio_bancao.controller;

import com.danbramos.desafio_bancao.model.Transaction;
import com.danbramos.desafio_bancao.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/transacao")
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Void> addTransaction (@RequestBody Transaction transaction) {
        if(transaction.value() < 0.0) {
            log.error("Valor da transação precisa ser maior que zero");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        if(transaction.dateTime().isAfter(OffsetDateTime.now())) {
            log.error("Transação não pode ocorrer no futuro");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        transactionService.add(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTransactions () {
        transactionService.delete();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
