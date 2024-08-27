package org.java.mentorship.budget.service;

import org.java.mentorship.budget.domain.TransactionEntity;
import org.java.mentorship.budget.exception.NoEntityFoundException;
import org.java.mentorship.budget.persistence.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository repository;

    @Test
    void saveShouldValidateAndSaveTransaction() {
        TransactionEntity transactionEntity = new TransactionEntity();
        when(repository.save(any(TransactionEntity.class))).thenReturn(transactionEntity);

        TransactionEntity result = transactionService.save(transactionEntity);

        assertEquals(transactionEntity, result);
        verify(repository).save(transactionEntity);
    }

    @Test
    void findAllShouldCallRepository() {
        List<TransactionEntity> transactions = Arrays.asList(new TransactionEntity(), new TransactionEntity());
        when(repository.findAll()).thenReturn(transactions);

        List<TransactionEntity> result = transactionService.findAll();

        assertEquals(transactions, result);
        verify(repository).findAll();
    }

    @Test
    void findByIdShouldReturnTransactionWhenExists() {
        TransactionEntity transactionEntity = new TransactionEntity();
        when(repository.findById(1)).thenReturn(transactionEntity);

        TransactionEntity result = transactionService.findById(1);

        assertEquals(transactionEntity, result);
        verify(repository).findById(1);
    }

    @Test
    void findByIdShouldThrowExceptionWhenNotFound() {
        when(repository.findById(1)).thenThrow(EmptyResultDataAccessException.class);

        assertThrows(NoEntityFoundException.class, () -> transactionService.findById(1));
        verify(repository).findById(1);
    }

    @Test
    void updateShouldValidateAndUpdateTransactionWhenExists() {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(1);
        when(repository.findById(1)).thenReturn(transactionEntity);
        when(repository.update(any(TransactionEntity.class))).thenReturn(transactionEntity);

        TransactionEntity result = transactionService.update(transactionEntity);

        assertEquals(transactionEntity, result);
        verify(repository).findById(1);
        verify(repository).update(transactionEntity);
    }

    @Test
    void updateShouldThrowExceptionWhenTransactionNotFound() {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(1);
        when(repository.findById(1)).thenThrow(EmptyResultDataAccessException.class);

        assertThrows(NoEntityFoundException.class, () -> transactionService.update(transactionEntity));
        verify(repository).findById(1);
        verify(repository, never()).update(any(TransactionEntity.class));
    }

    @Test
    void deleteShouldReturnDeletedTransactionWhenExists() {
        TransactionEntity transactionEntity = new TransactionEntity();
        when(repository.findById(1)).thenReturn(transactionEntity);
        when(repository.delete(1)).thenReturn(transactionEntity);

        TransactionEntity result = transactionService.delete(1);

        assertEquals(transactionEntity, result);
        verify(repository).findById(1);
        verify(repository).delete(1);
    }

    @Test
    void deleteShouldThrowExceptionWhenTransactionNotFound() {
        when(repository.findById(1)).thenThrow(EmptyResultDataAccessException.class);

        assertThrows(NoEntityFoundException.class, () -> transactionService.delete(1));
        verify(repository).findById(1);
        verify(repository, never()).delete(1);
    }
}
