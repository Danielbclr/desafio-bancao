package com.danbramos.desafio_bancao.service;

import com.danbramos.desafio_bancao.model.Stats;
import com.danbramos.desafio_bancao.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class StatService {
    private static final Logger log = LoggerFactory.getLogger(StatService.class);
    public final TransactionService transactionService;

    public StatService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Stats getStats(int intervalInS) {
        log.info("Capturando estatísticas das transações realizadas nos últimos " + intervalInS + " segundos");
        List<Transaction> transactionList = transactionService.getListFromInterval(intervalInS);
        if(transactionList.isEmpty()) {
            log.info("Lista está vazia, retornando estatísticas default");
            return new Stats(0L, 0.0, 0.0, 0.0, 0.0);
        }
        DoubleSummaryStatistics stats = transactionList.stream()
                .mapToDouble(Transaction::value).summaryStatistics();
        return new Stats(stats.getCount(), stats.getSum(), stats.getAverage(), stats.getMin(), stats.getMax());
    }
}
