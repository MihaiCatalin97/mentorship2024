package org.java.mentorship.budget.service;

import org.java.mentorship.budget.domain.BankAccountEntity;
import org.java.mentorship.budget.exception.NoEntityFoundException;
import org.java.mentorship.budget.persistence.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository repository;

    @Test
    void saveShouldValidateAndSaveAccount() {
        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        when(repository.save(any(BankAccountEntity.class))).thenReturn(bankAccountEntity);

        accountService.save(bankAccountEntity);

        verify(repository).save(bankAccountEntity);
    }

    @Test
    void findAllShouldCallRepository() {
        List<BankAccountEntity> accounts = Arrays.asList(new BankAccountEntity(), new BankAccountEntity());
        when(repository.findAll()).thenReturn(accounts);

        List<BankAccountEntity> result = accountService.findAll();

        assertEquals(accounts, result);
        verify(repository).findAll();
    }

    @Test
    void findByIdShouldReturnAccountWhenExists() {
        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        when(repository.findById(1)).thenReturn(bankAccountEntity);

        BankAccountEntity result = accountService.findById(1);

        assertEquals(bankAccountEntity, result);
        verify(repository).findById(1);
    }

    @Test
    void findByIdShouldThrowExceptionWhenNotFound() {
        when(repository.findById(1)).thenReturn(null);

        assertThrows(NoEntityFoundException.class, () -> accountService.findById(1));
        verify(repository).findById(1);
    }

    @Test
    void updateShouldValidateAndUpdateAccountWhenExists() {
        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setId(1);
        when(repository.findById(1)).thenReturn(bankAccountEntity);
        when(repository.update(any(BankAccountEntity.class))).thenReturn(bankAccountEntity);

        accountService.update(bankAccountEntity);

        verify(repository).findById(1);
        verify(repository).update(bankAccountEntity);
    }

    @Test
    void updateShouldThrowExceptionWhenAccountNotFound() {
        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setId(1);
        when(repository.findById(1)).thenReturn(null);

        assertThrows(NoEntityFoundException.class, () -> accountService.update(bankAccountEntity));
        verify(repository).findById(1);
        verify(repository, never()).update(any(BankAccountEntity.class));
    }

    @Test
    void deleteShouldReturnDeletedAccountWhenExists() {
        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        when(repository.findById(1)).thenReturn(bankAccountEntity);
        when(repository.delete(1)).thenReturn(bankAccountEntity);

        BankAccountEntity result = accountService.delete(1);

        assertEquals(bankAccountEntity, result);
        verify(repository).findById(1);
        verify(repository).delete(1);
    }

    @Test
    void deleteShouldThrowExceptionWhenAccountNotFound() {
        when(repository.findById(1)).thenReturn(null);

        assertThrows(NoEntityFoundException.class, () -> accountService.delete(1));
        verify(repository).findById(1);
        verify(repository, never()).delete(1);
    }
}
