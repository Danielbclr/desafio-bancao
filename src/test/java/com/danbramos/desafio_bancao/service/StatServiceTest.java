package com.danbramos.desafio_bancao.service;

import com.danbramos.desafio_bancao.dtos.StatsDTO;
import com.danbramos.desafio_bancao.dtos.TransactionDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatServiceTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private StatService statService;

    @Test
    void getStats_WithEmptyTransactionList_ShouldReturnZeroStats() {
        // Arrange
        when(transactionService.getListFromInterval(anyInt())).thenReturn(Collections.emptyList());
        when(transactionService.getList()).thenReturn(Collections.emptyList());

        // Act
        StatsDTO statsDefaultInterval = statService.getStats(60);
        StatsDTO statsZeroInterval = statService.getStats(0); // Test the "all time" case

        // Assert
        assertNotNull(statsDefaultInterval);
        assertEquals(0L, statsDefaultInterval.count());
        assertEquals(0.0, statsDefaultInterval.sum());
        assertEquals(0.0, statsDefaultInterval.avg());
        assertEquals(0.0, statsDefaultInterval.min());
        assertEquals(0.0, statsDefaultInterval.max());

        assertNotNull(statsZeroInterval);
        assertEquals(0L, statsZeroInterval.count());
        assertEquals(0.0, statsZeroInterval.sum());
        assertEquals(0.0, statsZeroInterval.avg());
        assertEquals(0.0, statsZeroInterval.min());
        assertEquals(0.0, statsZeroInterval.max());
    }

    @Test
    void getStats_WithTransactions_ShouldReturnCorrectStats() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();
        List<TransactionDTO> transactions = List.of(
                new TransactionDTO(100.50, now.minusSeconds(10)),
                new TransactionDTO(50.0, now.minusSeconds(20)),
                new TransactionDTO(200.0, now.minusSeconds(30))
        );
        when(transactionService.getListFromInterval(60)).thenReturn(transactions);

        // Act
        StatsDTO stats = statService.getStats(60);

        // Assert
        assertNotNull(stats);
        assertEquals(3L, stats.count());
        assertEquals(350.5, stats.sum(), 0.001);
        assertEquals(350.5 / 3.0, stats.avg(), 0.001);
        assertEquals(50.0, stats.min(), 0.001);
        assertEquals(200.0, stats.max(), 0.001);
    }

    @Test
    void getStats_WithSpecificInterval_ShouldCallCorrectServiceMethod() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();
        List<TransactionDTO> transactions = List.of(
                new TransactionDTO(100.0, now.minusSeconds(15))
        );
        int interval = 30;
        when(transactionService.getListFromInterval(interval)).thenReturn(transactions);

        // Act
        StatsDTO stats = statService.getStats(interval);

        // Assert
        assertNotNull(stats);
        assertEquals(1L, stats.count());
        assertEquals(100.0, stats.sum());
        assertEquals(100.0, stats.avg());
        assertEquals(100.0, stats.min());
        assertEquals(100.0, stats.max());
    }

    @Test
    void getStats_WithZeroInterval_ShouldCallGetList() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();
        List<TransactionDTO> transactions = List.of(
            new TransactionDTO(100.0, now.minusSeconds(10)),
            new TransactionDTO(200.0, now.minusSeconds(100))
        );
        when(transactionService.getList()).thenReturn(transactions);

        // Act
        StatsDTO stats = statService.getStats(0);

        // Assert
        assertNotNull(stats);
        assertEquals(2L, stats.count());
        assertEquals(300.0, stats.sum());
        assertEquals(150.0, stats.avg());
        assertEquals(100.0, stats.min());
        assertEquals(200.0, stats.max());
    }

    @Test
    void getStats_WithNegativeInterval_ShouldDefaultTo60Seconds() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();
        List<TransactionDTO> transactions = List.of(
                new TransactionDTO(100.0, now.minusSeconds(45))
        );
        when(transactionService.getListFromInterval(60)).thenReturn(transactions);

        // Act
        StatsDTO stats = statService.getStats(-10);

        // Assert
        assertNotNull(stats);
        assertEquals(1L, stats.count());
        assertEquals(100.0, stats.sum());
    }
} 