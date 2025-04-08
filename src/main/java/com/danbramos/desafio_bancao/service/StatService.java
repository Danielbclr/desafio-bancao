package com.danbramos.desafio_bancao.service;

import com.danbramos.desafio_bancao.dtos.StatsDTO;
import com.danbramos.desafio_bancao.dtos.TransactionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * Service class for calculating statistics on transactions.
 * This class provides methods to retrieve statistical data (count, sum, average, min, max)
 * for transactions within a specified time interval.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class StatService {
    public final TransactionService transactionService;

    /**
     * Retrieves statistical data for transactions within a specified time interval.
     *
     * @param intervalInS The time interval in seconds to consider for calculating statistics.
     * @return A StatsDTO object containing the count, sum, average, minimum, and maximum values of transactions
     * within the specified interval.
     */
    public StatsDTO getStats(int intervalInS) {
        if (intervalInS < 0) {
            intervalInS = 60;
        }
        List<TransactionDTO> transactionDTOList;
        if(intervalInS == 0) {
            log.info("Capturando estatísticas das transações realizadas desde sempre");
            transactionDTOList = transactionService.getList();
        } else {
            log.info("Capturando estatísticas das transações realizadas nos últimos {} segundos", intervalInS);
            transactionDTOList = transactionService.getListFromInterval(intervalInS);
        }
        if (transactionDTOList.isEmpty()) {
            log.info("Lista está vazia, retornando estatísticas default");
            return new StatsDTO(0L, 0.0, 0.0, 0.0, 0.0);
        }
        DoubleSummaryStatistics stats = transactionDTOList.stream()
                .mapToDouble(TransactionDTO::value).summaryStatistics();
        return new StatsDTO(stats.getCount(), stats.getSum(), stats.getAverage(), stats.getMin(), stats.getMax());
    }
}