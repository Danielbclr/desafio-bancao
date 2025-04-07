package com.danbramos.desafio_bancao.controller;

import com.danbramos.desafio_bancao.dtos.TransactionDTO;
import com.danbramos.desafio_bancao.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for managing transactions.
 * This class provides endpoints to add, delete and get transactions.
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/transacao")
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Adds a new transaction.
     *
     * @param transactionDTO The transaction to be added.
     * @return ResponseEntity with HTTP status CREATED if the transaction is added successfully,
     * or UNPROCESSABLE_ENTITY with error details if the transaction value is negative or the transaction date is in the future.
     */
    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody TransactionDTO transactionDTO) {
        if (transactionDTO.value() < 0.0) {
            log.error("Transaction value must be greater than or equal to zero");
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Transaction value must be greater than or equal to zero");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
        }
        if (transactionDTO.dateTime().isAfter(OffsetDateTime.now())) {
            log.error("Transaction cannot occur in the future");
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Transaction cannot occur in the future");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
        }
        transactionService.add(transactionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Deletes all transactions.
     *
     * @return ResponseEntity with HTTP status OK.
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteTransactions() {
        transactionService.delete();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}