package org.java.mentorship.budget.domain.mapper;

import org.java.mentorship.budget.domain.BankAccountEntity;
import org.java.mentorship.contracts.budget.dto.Account;
import org.java.mentorship.contracts.budget.dto.AccountType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountContractMapperTest {

    @Test
    void entityToContractShouldMapFieldsCorrectly() {
        // Given
        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setId(1);
        bankAccountEntity.setUserId(100);
        bankAccountEntity.setName("Savings Account");
        bankAccountEntity.setType(AccountType.SAVINGS);
        bankAccountEntity.setBalance(1000);
        bankAccountEntity.setCurrency("USD");

        // When
        Account accountContract = AccountContractMapper.entityToContract(bankAccountEntity);

        // Then
        assertNotNull(accountContract);
        assertEquals(1, accountContract.getId());
        assertEquals(100, accountContract.getUserId());
        assertEquals("Savings Account", accountContract.getName());
        assertEquals(AccountType.SAVINGS, accountContract.getType());
        assertEquals(1000, accountContract.getBalance());
        assertEquals("USD", accountContract.getCurrency());
    }

    @Test
    void contractToEntityShouldMapFieldsCorrectly() {
        // Given
        Account accountContract = new Account();
        accountContract.setId(1);
        accountContract.setUserId(100);
        accountContract.setName("Savings Account");
        accountContract.setType(AccountType.SAVINGS);
        accountContract.setBalance(1000);
        accountContract.setCurrency("USD");

        // When
        BankAccountEntity bankAccountEntity = AccountContractMapper.contractToEntity(accountContract);

        // Then
        assertNotNull(bankAccountEntity);
        assertEquals(1, bankAccountEntity.getId());
        assertEquals(100, bankAccountEntity.getUserId());
        assertEquals("Savings Account", bankAccountEntity.getName());
        assertEquals(AccountType.SAVINGS, bankAccountEntity.getType());
        assertEquals(1000, bankAccountEntity.getBalance());
        assertEquals("USD", bankAccountEntity.getCurrency());
    }
}
