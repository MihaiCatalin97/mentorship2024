package org.java.mentorship.budget.persistence.mapper;

import org.java.mentorship.budget.domain.TransactionEntity;
import org.java.mentorship.contracts.budget.dto.TransactionType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionRowMapperTest {

    private final TransactionRowMapper rowMapper = new TransactionRowMapper();

    @Test
    void shouldMapRowToTransactionEntity() throws SQLException {
        // Given
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        OffsetDateTime timestamp = OffsetDateTime.now();

        // Mocking the behavior of ResultSet
        Mockito.when(resultSet.getInt("id")).thenReturn(1);
        Mockito.when(resultSet.getInt("user_id")).thenReturn(2);
        Mockito.when(resultSet.getString("type")).thenReturn(TransactionType.INCOME.name());
        Mockito.when(resultSet.getInt("transaction_value")).thenReturn(1500);
        Mockito.when(resultSet.getString("description")).thenReturn("Payment for services");
        Mockito.when(resultSet.getInt("account_id")).thenReturn(456);
        Mockito.when(resultSet.getObject("timestamp", OffsetDateTime.class)).thenReturn(timestamp);

        // When
        TransactionEntity transactionEntity = rowMapper.mapRow(resultSet, 1);

        // Then
        assertEquals(1, transactionEntity.getId());
        assertEquals(2, transactionEntity.getUserId());
        assertEquals(TransactionType.INCOME, transactionEntity.getType());
        assertEquals(1500, transactionEntity.getValue());
        assertEquals("Payment for services", transactionEntity.getDescription());
        assertEquals(456, transactionEntity.getAccountId());
        assertEquals(timestamp, transactionEntity.getTimestamp());
    }
}
