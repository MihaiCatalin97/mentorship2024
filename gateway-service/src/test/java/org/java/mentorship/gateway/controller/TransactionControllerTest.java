package org.java.mentorship.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.java.mentorship.contracts.budget.client.TransactionFeignClient;
import org.java.mentorship.contracts.budget.dto.Transaction;
import org.java.mentorship.contracts.budget.dto.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest extends AbstractControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionFeignClient transactionFeignClient;

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void testGetTransactions(final String sessionHeader) throws Exception {
        Transaction transaction1 = Transaction.builder()
                .id(1)
                .userId(123)
                .type(TransactionType.INCOME)
                .value(100)
                .description("Salary")
                .categoryId(1)
                .accountId(1)
                .timestamp(OffsetDateTime.now())
                .build();

        Transaction transaction2 = Transaction.builder()
                .id(2)
                .userId(123)
                .type(TransactionType.EXPENSE)
                .value(50)
                .description("Dinner")
                .categoryId(2)
                .accountId(1)
                .timestamp(OffsetDateTime.now())
                .build();

        when(transactionFeignClient.getTransactions()).thenReturn(Arrays.asList(transaction1, transaction2));

        mockMvc.perform(get("/transactions")
                        .header("X-SESSION", sessionHeader)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].description").value("Salary"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].description").value("Dinner"));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void testGetTransactionById(final String sessionHeader) throws Exception {
        Transaction transaction = Transaction.builder()
                .id(1)
                .userId(123)
                .type(TransactionType.INCOME)
                .value(100)
                .description("Salary")
                .categoryId(1)
                .accountId(1)
                .timestamp(OffsetDateTime.now())
                .build();

        when(transactionFeignClient.getTransactionById(anyInt())).thenReturn(transaction);

        mockMvc.perform(get("/transactions/1")
                        .header("X-SESSION", sessionHeader)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Salary"));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void testCreateTransaction(final String sessionHeader) throws Exception {
        Transaction transaction = Transaction.builder()
                .id(1)
                .userId(123)
                .type(TransactionType.INCOME)
                .value(100)
                .description("Salary")
                .categoryId(1)
                .accountId(1)
                .timestamp(OffsetDateTime.now())
                .build();

        when(transactionFeignClient.createTransaction(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/transactions")
                        .header("X-SESSION", sessionHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Salary"));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void testUpdateTransaction(final String sessionHeader) throws Exception {
        Transaction transaction = Transaction.builder()
                .id(1)
                .userId(123)
                .type(TransactionType.INCOME)
                .value(100)
                .description("Salary")
                .categoryId(1)
                .accountId(1)
                .timestamp(OffsetDateTime.now())
                .build();

        when(transactionFeignClient.updateTransaction(anyInt(), any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(put("/transactions/1")
                        .header("X-SESSION", sessionHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Salary"));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void testDeleteTransaction(final String sessionHeader) throws Exception {
        mockMvc.perform(delete("/transactions/1")
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isNoContent());
    }
}
