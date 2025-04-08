package com.danbramos.desafio_bancao.service;

import com.danbramos.desafio_bancao.dtos.TransactionDTO;
import com.danbramos.desafio_bancao.exception.UnprocessableEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing transactions.
 * This class provides methods to add, delete, and retrieve transactions within
 * a specific time interval.
 */
@Service
@Slf4j
public class TransactionService {
    private final List<TransactionDTO> transactionDTOList = new ArrayList<>();

    /**
     * Adds a new transaction to the transaction list.
     *
     * @param transactionDTO The transaction to be added.
     */
    public void add(TransactionDTO transactionDTO) {
        if (transactionDTO.value() < 0.0) {
            throw new UnprocessableEntityException("Valor da transação deve ser maior que zero");
        }
        if (transactionDTO.dateTime().isAfter(OffsetDateTime.now())) {
            throw new UnprocessableEntityException("Transação não pode ocorrer no futuro");
        }
        log.info("Adicionando transação de valor " + transactionDTO.value() + " em: " + transactionDTO.dateTime());
        transactionDTOList.add(transactionDTO);
    }

    /**
     * Deletes all transactions from the transaction list.
     */
    public void delete() {
        log.info("Limpando lista de transações");
        transactionDTOList.clear();
    }

    /**
     * Retrieves a list of transactions that occurred since the beginning.
     *
     * @return List of all transactions.
     */
    public List<TransactionDTO> getList() {
        log.info("Buscando transações desde sempre");
        return transactionDTOList;
    }

    /**
     * Retrieves a list of transactions that occurred within a specified time interval.
     *
     * @param intervalInS The time interval in seconds to consider.
     * @return A list of transactions that occurred within the specified interval.
     */
    public List<TransactionDTO> getListFromInterval(int intervalInS) {
        log.info("Buscando transações nos últimos {} segundos", intervalInS);
        OffsetDateTime dateTimeInterval = OffsetDateTime.now().minusSeconds(intervalInS);
        return transactionDTOList.stream().filter(transactionDTO ->
                transactionDTO.dateTime().isAfter(dateTimeInterval)).toList();
    }
}
