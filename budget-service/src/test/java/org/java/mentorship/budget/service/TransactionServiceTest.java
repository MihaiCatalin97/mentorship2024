package org.java.mentorship.budget.service;

import org.java.mentorship.budget.domain.BankAccountEntity;
import org.java.mentorship.budget.domain.TransactionEntity;
import org.java.mentorship.budget.exception.NoEntityFoundException;
import org.java.mentorship.budget.persistence.TransactionRepository;
import org.java.mentorship.contracts.budget.dto.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private AccountService bankAccountService;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveShouldSaveTransactionAndUpdateAccountBalance() {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(1);
        transaction.setAccountId(1);
        transaction.setValue(100);
        transaction.setType(TransactionType.INCOME);

        BankAccountEntity account = new BankAccountEntity();
        account.setId(1);
        account.setBalance(1000);

        when(repository.save(transaction)).thenReturn(transaction);
        when(bankAccountService.findById(transaction.getAccountId())).thenReturn(account);

        TransactionEntity result = transactionService.save(transaction);

        assertNotNull(result);
        assertEquals(transaction, result);
        assertEquals(1100, account.getBalance());
        verify(repository, times(1)).save(transaction);
        verify(bankAccountService, times(1)).update(account);
    }

    @Test
    void findAllShouldReturnAllTransactions() {
        TransactionEntity transaction = new TransactionEntity();
        when(repository.findAll()).thenReturn(Collections.singletonList(transaction));

        List<TransactionEntity> transactions = transactionService.findAll();

        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());
        assertEquals(1, transactions.size());
    }

    @Test
    void findByIdShouldReturnTransaction() {
        TransactionEntity transaction = new TransactionEntity();
        when(repository.findById(1)).thenReturn(transaction);

        TransactionEntity result = transactionService.findById(1);

        assertNotNull(result);
        assertEquals(transaction, result);
    }

    @Test
    void findByIdShouldThrowNoEntityFoundExceptionWhenNotFound() {
        when(repository.findById(1)).thenThrow(new EmptyResultDataAccessException(1));

        NoEntityFoundException thrown = assertThrows(NoEntityFoundException.class, () -> transactionService.findById(1));
        assertEquals("Transaction with id 1 not found", thrown.getMessage());
    }

    @Test
    void updateShouldUpdateTransactionAndAdjustAccountBalance() {
        TransactionEntity oldTransaction = new TransactionEntity();
        oldTransaction.setId(1);
        oldTransaction.setAccountId(1);
        oldTransaction.setValue(100);
        oldTransaction.setType(TransactionType.INCOME);

        TransactionEntity newTransaction = new TransactionEntity();
        newTransaction.setId(1);
        newTransaction.setAccountId(1);
        newTransaction.setValue(200);
        newTransaction.setType(TransactionType.EXPENSE);

        BankAccountEntity account = new BankAccountEntity();
        account.setId(1);
        account.setBalance(1000);

        when(repository.findById(1)).thenReturn(oldTransaction);
        when(repository.update(newTransaction)).thenReturn(newTransaction);
        when(bankAccountService.findById(1)).thenReturn(account);

        TransactionEntity result = transactionService.update(newTransaction);

        assertNotNull(result);
        assertEquals(newTransaction, result);
        assertEquals(700, account.getBalance());
        verify(repository, times(1)).update(newTransaction);
        verify(bankAccountService, times(1)).update(account);
    }

    @Test
    void updateShouldThrowNoEntityFoundExceptionWhenNotFound() {
        TransactionEntity newTransaction = new TransactionEntity();
        newTransaction.setId(1);

        when(repository.findById(1)).thenThrow(new EmptyResultDataAccessException(1));

        NoEntityFoundException thrown = assertThrows(NoEntityFoundException.class, () -> transactionService.update(newTransaction));
        assertEquals("Transaction with id 1 not found", thrown.getMessage());
    }

    @Test
    void deleteShouldDeleteTransactionAndUpdateAccountBalance() {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(1);
        transaction.setAccountId(1);
        transaction.setValue(100);
        transaction.setType(TransactionType.INCOME);

        BankAccountEntity account = new BankAccountEntity();
        account.setId(1);
        account.setBalance(1000);

        when(repository.findById(1)).thenReturn(transaction);
        when(bankAccountService.findById(1)).thenReturn(account);

        TransactionEntity result = transactionService.delete(1);

        assertNotNull(result);
        assertEquals(transaction, result);
        assertEquals(900, account.getBalance());
        verify(repository, times(1)).delete(1);
        verify(bankAccountService, times(1)).update(account);
    }

    @Test
    void deleteShouldThrowNoEntityFoundExceptionWhenNotFound() {
        when(repository.findById(1)).thenThrow(new EmptyResultDataAccessException(1));

        NoEntityFoundException thrown = assertThrows(NoEntityFoundException.class, () -> transactionService.delete(1));
        assertEquals("Transaction with id 1 not found", thrown.getMessage());
    }

    @Test
    void findAllWithFiltersShouldReturnRecentTransactions() {
        TransactionEntity transaction = new TransactionEntity();
        when(repository.findTransactionsFromLast7Days()).thenReturn(Collections.singletonList(transaction));

        List<TransactionEntity> transactions = transactionService.findAllWithFilters(true);

        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());
        assertEquals(1, transactions.size());
        verify(repository, times(1)).findTransactionsFromLast7Days();
    }

    @Test
    void findAllWithFiltersShouldReturnAllTransactions() {
        TransactionEntity transaction = new TransactionEntity();
        when(repository.findAll()).thenReturn(Collections.singletonList(transaction));

        List<TransactionEntity> transactions = transactionService.findAllWithFilters(false);

        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());
        assertEquals(1, transactions.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void adjustAccountBalanceForUpdateShouldHandleIncomeToIncome() {
        TransactionEntity oldTransaction = new TransactionEntity();
        oldTransaction.setId(1);
        oldTransaction.setAccountId(1);
        oldTransaction.setValue(100);
        oldTransaction.setType(TransactionType.INCOME);

        TransactionEntity newTransaction = new TransactionEntity();
        newTransaction.setId(1);
        newTransaction.setAccountId(1);
        newTransaction.setValue(200);
        newTransaction.setType(TransactionType.INCOME);

        BankAccountEntity account = new BankAccountEntity();
        account.setId(1);
        account.setBalance(1000);

        when(repository.findById(1)).thenReturn(oldTransaction);
        when(repository.update(newTransaction)).thenReturn(newTransaction);
        when(bankAccountService.findById(1)).thenReturn(account);

        transactionService.update(newTransaction);

        assertEquals(1100, account.getBalance());
    }

    @Test
    void adjustAccountBalanceForUpdateShouldHandleExpenseToExpense() {
        TransactionEntity oldTransaction = new TransactionEntity();
        oldTransaction.setId(1);
        oldTransaction.setAccountId(1);
        oldTransaction.setValue(100);
        oldTransaction.setType(TransactionType.EXPENSE);

        TransactionEntity newTransaction = new TransactionEntity();
        newTransaction.setId(1);
        newTransaction.setAccountId(1);
        newTransaction.setValue(200);
        newTransaction.setType(TransactionType.EXPENSE);

        BankAccountEntity account = new BankAccountEntity();
        account.setId(1);
        account.setBalance(1000);

        when(repository.findById(1)).thenReturn(oldTransaction);
        when(repository.update(newTransaction)).thenReturn(newTransaction);
        when(bankAccountService.findById(1)).thenReturn(account);

        transactionService.update(newTransaction);

        assertEquals(900, account.getBalance());
    }

    @Test
    void adjustAccountBalanceForUpdateShouldHandleIncomeToExpense() {
        TransactionEntity oldTransaction = new TransactionEntity();
        oldTransaction.setId(1);
        oldTransaction.setAccountId(1);
        oldTransaction.setValue(100);
        oldTransaction.setType(TransactionType.INCOME);

        TransactionEntity newTransaction = new TransactionEntity();
        newTransaction.setId(1);
        newTransaction.setAccountId(1);
        newTransaction.setValue(200);
        newTransaction.setType(TransactionType.EXPENSE);

        BankAccountEntity account = new BankAccountEntity();
        account.setId(1);
        account.setBalance(1000);

        when(repository.findById(1)).thenReturn(oldTransaction);
        when(repository.update(newTransaction)).thenReturn(newTransaction);
        when(bankAccountService.findById(1)).thenReturn(account);

        transactionService.update(newTransaction);

        assertEquals(700, account.getBalance());
    }

    @Test
    void adjustAccountBalanceForUpdateShouldHandleExpenseToIncome() {
        TransactionEntity oldTransaction = new TransactionEntity();
        oldTransaction.setId(1);
        oldTransaction.setAccountId(1);
        oldTransaction.setValue(100);
        oldTransaction.setType(TransactionType.EXPENSE);

        TransactionEntity newTransaction = new TransactionEntity();
        newTransaction.setId(1);
        newTransaction.setAccountId(1);
        newTransaction.setValue(200);
        newTransaction.setType(TransactionType.INCOME);

        BankAccountEntity account = new BankAccountEntity();
        account.setId(1);
        account.setBalance(1000);

        when(repository.findById(1)).thenReturn(oldTransaction);
        when(repository.update(newTransaction)).thenReturn(newTransaction);
        when(bankAccountService.findById(1)).thenReturn(account);

        transactionService.update(newTransaction);

        assertEquals(1300, account.getBalance());
    }
}
