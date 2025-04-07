package com.danbramos.desafio_bancao.service;

import com.danbramos.desafio_bancao.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    private final List<Transaction> transactionList = new ArrayList<>();

    public void add(Transaction transaction) {
        log.info("Adicionando transação de valor " + transaction.value() + " em: " + transaction.dateTime());
        transactionList.add(transaction);
    }

    public void delete () {
        log.info("Limpando lista de transações");
        transactionList.clear();
    }

    public List<Transaction> getListFromInterval(int intervalInS) {
        log.info("Buscando transações nos últimos " + intervalInS + " segundos");
        OffsetDateTime dateTimeInterval = OffsetDateTime.now().minusSeconds(intervalInS);
        return transactionList.stream().filter(transaction ->
                transaction.dateTime().isAfter(dateTimeInterval)).toList();
    }
}
