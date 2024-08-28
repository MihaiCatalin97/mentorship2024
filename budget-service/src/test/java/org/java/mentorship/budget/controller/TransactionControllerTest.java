package org.java.mentorship.budget.controller;

import org.java.mentorship.budget.domain.TransactionEntity;
import org.java.mentorship.budget.domain.mapper.TransactionContractMapper;
import org.java.mentorship.budget.service.TransactionService;
import org.java.mentorship.contracts.budget.dto.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private List<TransactionEntity> transactionEntities;

    @BeforeEach
    void setupMocks() {
        transactionEntities = Arrays.asList(
                TransactionEntity.builder().id(1).description("Transaction 1").build(),
                TransactionEntity.builder().id(2).description("Transaction 2").build()
        );
    }

    @Test
    void getAllTransactionsShouldReturnContractsFromTransactionService() {
        // Given
        when(transactionService.findAllWithFilters(null)).thenReturn(transactionEntities);

        List<Transaction> transactions = transactionEntities.stream()
                .map(TransactionContractMapper::entityToContract)
                .toList();

        // When
        ResponseEntity<List<Transaction>> response = transactionController.getAllTransactions(null);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(transactions.get(0).getId(), response.getBody().get(0).getId());
    }

    @Test
    void getAllTransactionsWithRecentFilterShouldReturnFilteredTransactions() {
        // Given
        when(transactionService.findAllWithFilters(true)).thenReturn(transactionEntities);

        List<Transaction> transactions = transactionEntities.stream()
                .map(TransactionContractMapper::entityToContract)
                .toList();

        // When
        ResponseEntity<List<Transaction>> response = transactionController.getAllTransactions(true);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(transactions.get(0).getId(), response.getBody().get(0).getId());
    }

    @Test
    void getTransactionByIdShouldReturnContractFromTransactionService() {
        // Given
        when(transactionService.findById(anyInt())).thenReturn(transactionEntities.get(0));

        // When
        ResponseEntity<Transaction> response = transactionController.getTransactionById(1);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Transaction 1", response.getBody().getDescription());
    }

    @Test
    void createTransactionShouldReturnCreatedTransaction() {
        // Given
        Transaction transaction = Transaction.builder().description("New Transaction").build();
        TransactionEntity transactionEntity = TransactionContractMapper.contractToEntity(transaction);

        when(transactionService.save(any(TransactionEntity.class)))
                .thenReturn(TransactionEntity.builder().id(1).description("New Transaction").build());

        // When
        ResponseEntity<Transaction> response = transactionController.createTransaction(transaction);

        // Then
        assertEquals(202, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("New Transaction", response.getBody().getDescription());
    }

    @Test
    void updateTransactionShouldReturnUpdatedTransaction() {
        // Given
        Transaction transaction = Transaction.builder().description("Updated Transaction").build();
        TransactionEntity transactionEntity = TransactionContractMapper.contractToEntity(transaction);
        transactionEntity.setId(1);

        when(transactionService.update(any(TransactionEntity.class)))
                .thenReturn(TransactionEntity.builder().id(1).description("Updated Transaction").build());

        // When
        ResponseEntity<Transaction> response = transactionController.updateTransaction(1, transaction);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Updated Transaction", response.getBody().getDescription());
    }

    @Test
    void deleteTransactionShouldReturnDeletedTransaction() {
        // Given
        when(transactionService.delete(anyInt()))
                .thenReturn(TransactionEntity.builder().id(1).description("Deleted Transaction").build());

        // When
        ResponseEntity<Transaction> response = transactionController.deleteTransaction(1);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Deleted Transaction", response.getBody().getDescription());
    }

    @Test
    void getTransactionByIdShouldReturn404WhenTransactionNotFound() {
        // Given
        when(transactionService.findById(anyInt())).thenThrow(new IllegalArgumentException("Transaction not found"));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> transactionController.getTransactionById(999));
    }

    @Test
    void deleteTransactionShouldReturn404WhenTransactionNotFound() {
        // Given
        when(transactionService.delete(anyInt())).thenThrow(new IllegalArgumentException("Transaction not found"));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> transactionController.deleteTransaction(999));
    }
}
