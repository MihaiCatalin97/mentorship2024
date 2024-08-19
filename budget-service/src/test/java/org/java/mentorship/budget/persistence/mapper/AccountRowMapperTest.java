package org.java.mentorship.budget.persistence.mapper;

import org.java.mentorship.budget.domain.BankAccountEntity;
import org.java.mentorship.contracts.budget.dto.AccountType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountRowMapperTest {

    private final AccountRowMapper rowMapper = new AccountRowMapper();

    @Test
    void shouldMapRowToBankAccountEntity() throws SQLException {
        // Given
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        // Mocking the behavior of ResultSet
        Mockito.when(resultSet.getInt("id")).thenReturn(1);
        Mockito.when(resultSet.getInt("user_id")).thenReturn(2);
        Mockito.when(resultSet.getString("name")).thenReturn("AccountName");
        Mockito.when(resultSet.getString("type")).thenReturn(AccountType.SAVINGS.name());
        Mockito.when(resultSet.getInt("balance")).thenReturn(1000);
        Mockito.when(resultSet.getString("currency")).thenReturn("USD");

        // When
        BankAccountEntity bankAccountEntity = rowMapper.mapRow(resultSet, 1);

        // Then
        assertEquals(1, bankAccountEntity.getId());
        assertEquals(2, bankAccountEntity.getUserId());
        assertEquals("AccountName", bankAccountEntity.getName());
        assertEquals(AccountType.SAVINGS, bankAccountEntity.getType());
        assertEquals(1000, bankAccountEntity.getBalance());
        assertEquals("USD", bankAccountEntity.getCurrency());
    }
}
