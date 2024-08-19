package org.java.mentorship.budget.controller;

import org.java.mentorship.budget.domain.BankAccountEntity;
import org.java.mentorship.budget.domain.mapper.AccountContractMapper;
import org.java.mentorship.budget.service.AccountService;
import org.java.mentorship.contracts.budget.dto.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private List<BankAccountEntity> accountEntities;

    @BeforeEach
    void setupMocks() {
        accountEntities = Arrays.asList(
                BankAccountEntity.builder().id(1).name("Account 1").build(),
                BankAccountEntity.builder().id(2).name("Account 2").build()
        );
    }

    @Test
    void getAllAccountsShouldReturnContractsFromAccountService() {
        when(accountService.findAll()).thenReturn(accountEntities);

        List<Account> accounts = accountEntities.stream()
                .map(AccountContractMapper::entityToContract)
                .toList();

        ResponseEntity<List<Account>> response = accountController.getAllAccounts();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(accounts.get(0).getId(), response.getBody().get(0).getId());
    }

    @Test
    void getAccountByIdShouldReturnContractFromAccountService() {
        when(accountService.findById(anyInt())).thenReturn(accountEntities.get(0));

        ResponseEntity<Account> response = accountController.getAccountById(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Account 1", response.getBody().getName());
    }

    @Test
    void createAccountShouldReturnCreatedAccount() {
        Account account = Account.builder().name("New Account").build();
        BankAccountEntity bankAccountEntity = AccountContractMapper.contractToEntity(account);

        when(accountService.save(any(BankAccountEntity.class)))
                .thenReturn(BankAccountEntity.builder().id(1).name("New Account").build());

        ResponseEntity<Account> response = accountController.createAccount(account);

        assertEquals(202, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("New Account", response.getBody().getName());
    }

    @Test
    void updateAccountShouldReturnUpdatedAccount() {
        Account account = Account.builder().name("Updated Account").build();
        BankAccountEntity bankAccountEntity = AccountContractMapper.contractToEntity(account);
        bankAccountEntity.setId(1);

        when(accountService.update(any(BankAccountEntity.class)))
                .thenReturn(BankAccountEntity.builder().id(1).name("Updated Account").build());

        ResponseEntity<Account> response = accountController.updateAccount(1, account);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Updated Account", response.getBody().getName());
    }

    @Test
    void deleteAccountShouldReturnDeletedAccount() {
        when(accountService.delete(anyInt()))
                .thenReturn(BankAccountEntity.builder().id(1).name("Deleted Account").build());

        ResponseEntity<Account> response = accountController.deleteAccount(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Deleted Account", response.getBody().getName());
    }

    @Test
    void getAccountByIdShouldReturn404WhenAccountNotFound() {
        when(accountService.findById(anyInt())).thenThrow(new IllegalArgumentException("Account not found"));

        assertThrows(IllegalArgumentException.class, () -> accountController.getAccountById(999));
    }

    @Test
    void deleteAccountShouldReturn404WhenAccountNotFound() {
        when(accountService.delete(anyInt())).thenThrow(new IllegalArgumentException("Account not found"));

        assertThrows(IllegalArgumentException.class, () -> accountController.deleteAccount(999));
    }
}
