package com.danbramos.desafio_bancao.controller.advice;

import com.danbramos.desafio_bancao.controller.TransactionController;
import com.danbramos.desafio_bancao.dtos.TransactionDTO;
import com.danbramos.desafio_bancao.exception.UnprocessableEntityException;
import com.danbramos.desafio_bancao.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class RestExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void handleUnprocessableEntity_ShouldReturnUnprocessableEntity() throws Exception {
        // Arrange
        doThrow(new UnprocessableEntityException("Test exception")).when(transactionService).add(any());
        String expectedErrorMessage = "Test exception";

        // Act
        ResultActions resultActions = mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new TransactionDTO(100.0, OffsetDateTime.now(ZoneOffset.UTC)))));

        // Assert
        assertUnprocessableEntityResponse(resultActions, expectedErrorMessage);
    }

    private void assertUnprocessableEntityResponse(ResultActions resultActions, String expectedErrorMessage) throws Exception {
        resultActions.andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(Map.of("error", expectedErrorMessage))));
    }
}