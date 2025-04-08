package com.danbramos.desafio_bancao.controller;

import com.danbramos.desafio_bancao.dtos.TransactionDTO;
import com.danbramos.desafio_bancao.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addTransaction_ValidTransaction_ShouldReturnCreated() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO(100.0, OffsetDateTime.now(ZoneOffset.UTC));

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isCreated());

        verify(transactionService, times(1)).add(transactionDTO);
    }

    @Test
    void addTransaction_InvalidJson_ShouldReturnBadRequest() throws Exception {
        String invalidJson = "{invalid: json}";

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());

        verify(transactionService, never()).add(any());
    }

    @Test
    void deleteTransactions_ShouldReturnOk() throws Exception {
        mockMvc.perform(delete("/transacao"))
                .andExpect(status().isOk());

        verify(transactionService, times(1)).delete();
    }
}