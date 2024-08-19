package org.java.mentorship.budget.persistence.mapper;

import org.java.mentorship.budget.domain.BudgetEntity;
import org.java.mentorship.contracts.budget.dto.BudgetInterval;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BudgetRowMapperTest {

    private final BudgetRowMapper rowMapper = new BudgetRowMapper();

    @Test
    void shouldMapRowToBudgetEntity() throws SQLException {
        // Given
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        // Mocking the behavior of ResultSet
        Mockito.when(resultSet.getInt("id")).thenReturn(1);
        Mockito.when(resultSet.getInt("user_id")).thenReturn(2);
        Mockito.when(resultSet.getString("name")).thenReturn("Monthly Budget");
        Mockito.when(resultSet.getInt("maximum_allowed")).thenReturn(5000);
        Mockito.when(resultSet.getString("budget_interval")).thenReturn(BudgetInterval.MONTHLY.name());
        Mockito.when(resultSet.getInt("transaction_id")).thenReturn(123);
        Mockito.when(resultSet.getInt("account_id")).thenReturn(456);

        // When
        BudgetEntity budgetEntity = rowMapper.mapRow(resultSet, 1);

        // Then
        assertEquals(1, budgetEntity.getId());
        assertEquals(2, budgetEntity.getUserId());
        assertEquals("Monthly Budget", budgetEntity.getName());
        assertEquals(5000, budgetEntity.getMaximumAllowed());
        assertEquals(BudgetInterval.MONTHLY, budgetEntity.getInterval());
        assertEquals(123, budgetEntity.getTransactionId());
        assertEquals(456, budgetEntity.getAccountId());
    }
}
