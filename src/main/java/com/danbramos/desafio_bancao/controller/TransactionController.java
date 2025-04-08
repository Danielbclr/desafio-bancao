package com.danbramos.desafio_bancao.controller;

import com.danbramos.desafio_bancao.dtos.TransactionDTO;
import com.danbramos.desafio_bancao.exception.UnprocessableEntityException;
import com.danbramos.desafio_bancao.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Transações", description = "Endpoints para gerenciamento de transações financeiras")
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
    @Operation(summary = "Adiciona uma nova transação", description = "Registra uma nova transação. Retorna 201 se bem-sucedido, ou 422 se a data for futura ou o valor for negativo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação adicionada com sucesso", content = @Content),
            @ApiResponse(responseCode = "422", description = "Erro de validação (valor negativo ou data futura)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class, example = "{\"error\": \"Transaction value must be greater than or equal to zero\"}"))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: JSON mal formatado)", content = @Content)
    })
    public ResponseEntity<?> addTransaction(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da transação a ser adicionada", required = true,
                    content = @Content(schema = @Schema(implementation = TransactionDTO.class)))
            @RequestBody TransactionDTO transactionDTO) {
        transactionService.add(transactionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Deletes all transactions.
     *
     * @return ResponseEntity with HTTP status OK.
     */
    @DeleteMapping
    @Operation(summary = "Remove todas as transações", description = "Exclui todos os registros de transações existentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transações removidas com sucesso", content = @Content)
    })
    public ResponseEntity<Void> deleteTransactions() {
        transactionService.delete();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}