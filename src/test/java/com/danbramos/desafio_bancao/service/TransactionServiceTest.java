package com.danbramos.desafio_bancao.service;

import com.danbramos.desafio_bancao.dtos.TransactionDTO;
import com.danbramos.desafio_bancao.exception.UnprocessableEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService();
    }

    @Test
    void add_ValidTransaction_ShouldAddToList() {
        TransactionDTO dto = new TransactionDTO(100.0, OffsetDateTime.now().minusSeconds(10));
        transactionService.add(dto);

        List<TransactionDTO> transactions = transactionService.getList();
        assertNotNull(transactions);
        assertEquals(1, transactions.size());
        assertEquals(dto, transactions.get(0));
    }

    @Test
    void add_NegativeValue_ShouldThrowUnprocessableEntityException() {
        TransactionDTO dto = new TransactionDTO(-50.0, OffsetDateTime.now());

        UnprocessableEntityException exception = assertThrows(
                UnprocessableEntityException.class,
                () -> transactionService.add(dto)
        );

        assertEquals("Valor da transação deve ser maior que zero", exception.getMessage());
        assertTrue(transactionService.getList().isEmpty());
    }

    @Test
    void add_FutureDate_ShouldThrowUnprocessableEntityException() {
        TransactionDTO dto = new TransactionDTO(100.0, OffsetDateTime.now().plusMinutes(5));

        UnprocessableEntityException exception = assertThrows(
                UnprocessableEntityException.class,
                () -> transactionService.add(dto)
        );

        assertEquals("Transação não pode ocorrer no futuro", exception.getMessage());
        assertTrue(transactionService.getList().isEmpty());
    }

    @Test
    void delete_WithExistingTransactions_ShouldClearList() {
        TransactionDTO dto1 = new TransactionDTO(100.0, OffsetDateTime.now().minusSeconds(10));
        TransactionDTO dto2 = new TransactionDTO(200.0, OffsetDateTime.now().minusSeconds(5));
        transactionService.add(dto1);
        transactionService.add(dto2);

        assertEquals(2, transactionService.getList().size());

        transactionService.delete();

        assertTrue(transactionService.getList().isEmpty());
    }

    @Test
    void delete_WithEmptyList_ShouldDoNothing() {
        assertTrue(transactionService.getList().isEmpty());
        assertDoesNotThrow(() -> transactionService.delete());
        assertTrue(transactionService.getList().isEmpty());
    }

    @Test
    void getList_ShouldReturnAllAddedTransactions() {
        TransactionDTO dto1 = new TransactionDTO(100.0, OffsetDateTime.now().minusSeconds(20));
        TransactionDTO dto2 = new TransactionDTO(200.0, OffsetDateTime.now().minusSeconds(10));
        transactionService.add(dto1);
        transactionService.add(dto2);

        List<TransactionDTO> transactions = transactionService.getList();
        assertNotNull(transactions);
        assertEquals(2, transactions.size());
        assertTrue(transactions.contains(dto1));
        assertTrue(transactions.contains(dto2));
    }

    @Test
    void getListFromInterval_ShouldReturnOnlyRecentTransactions() {
        OffsetDateTime now = OffsetDateTime.now();
        TransactionDTO recentDto1 = new TransactionDTO(100.0, now.minusSeconds(10));
        TransactionDTO recentDto2 = new TransactionDTO(150.0, now.minusSeconds(30));
        TransactionDTO oldDto = new TransactionDTO(200.0, now.minusSeconds(70));

        transactionService.add(recentDto1);
        transactionService.add(recentDto2);
        transactionService.add(oldDto);

        List<TransactionDTO> recentTransactions = transactionService.getListFromInterval(60);

        assertNotNull(recentTransactions);
        assertEquals(2, recentTransactions.size());
        assertTrue(recentTransactions.contains(recentDto1));
        assertTrue(recentTransactions.contains(recentDto2));
        assertFalse(recentTransactions.contains(oldDto));
    }

     @Test
    void getListFromInterval_CustomInterval_ShouldReturnMatchingTransactions() {
        OffsetDateTime now = OffsetDateTime.now();
        TransactionDTO dto1 = new TransactionDTO(100.0, now.minusSeconds(10));
        TransactionDTO dto2 = new TransactionDTO(150.0, now.minusSeconds(25));
        TransactionDTO dto3 = new TransactionDTO(200.0, now.minusSeconds(5));

        transactionService.add(dto1);
        transactionService.add(dto2);
        transactionService.add(dto3);

        List<TransactionDTO> recentTransactions = transactionService.getListFromInterval(20);

        assertNotNull(recentTransactions);
        assertEquals(2, recentTransactions.size());
        assertTrue(recentTransactions.contains(dto1));
        assertFalse(recentTransactions.contains(dto2));
        assertTrue(recentTransactions.contains(dto3));
    }

    @Test
    void getListFromInterval_NoMatchingTransactions_ShouldReturnEmptyList() {
        OffsetDateTime now = OffsetDateTime.now();
        TransactionDTO oldDto1 = new TransactionDTO(100.0, now.minusSeconds(70));
        TransactionDTO oldDto2 = new TransactionDTO(200.0, now.minusSeconds(80));

        transactionService.add(oldDto1);
        transactionService.add(oldDto2);

        List<TransactionDTO> recentTransactions = transactionService.getListFromInterval(60);

        assertNotNull(recentTransactions);
        assertTrue(recentTransactions.isEmpty());
    }

     @Test
    void getListFromInterval_EmptySourceList_ShouldReturnEmptyList() {
        List<TransactionDTO> recentTransactions = transactionService.getListFromInterval(60);
        assertNotNull(recentTransactions);
        assertTrue(recentTransactions.isEmpty());
    }
} 