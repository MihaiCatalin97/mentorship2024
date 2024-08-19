package org.java.mentorship.budget.domain.mapper;

import org.java.mentorship.budget.domain.TransactionEntity;
import org.java.mentorship.contracts.budget.dto.Transaction;
import org.java.mentorship.contracts.budget.dto.TransactionType;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class TransactionContractMapperTest {

    private static OffsetDateTime instantToOffsetDateTime(Instant instant) {
        return OffsetDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    private static Instant OffsetDateTimeToInstant(OffsetDateTime offsetDateTime) {
        return offsetDateTime.toInstant();
    }

    @Test
    void entityToContractShouldMapFieldsCorrectly() {
        // Given
        Instant now = Instant.now();
        OffsetDateTime nowOffsetDateTime = instantToOffsetDateTime(now);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(1);
        transactionEntity.setUserId(100);
        transactionEntity.setType(TransactionType.INCOME);
        transactionEntity.setValue(500);
        transactionEntity.setDescription("Salary");
        transactionEntity.setAccountId(10);
        transactionEntity.setTimestamp(nowOffsetDateTime);

        // When
        Transaction transactionContract = TransactionContractMapper.entityToContract(transactionEntity);

        // Then
        assertNotNull(transactionContract);
        assertEquals(1, transactionContract.getId());
        assertEquals(100, transactionContract.getUserId());
        assertEquals(TransactionType.INCOME, transactionContract.getType());
        assertEquals(500, transactionContract.getValue());
        assertEquals("Salary", transactionContract.getDescription());
        assertEquals(10, transactionContract.getAccountId());
        assertEquals(now, OffsetDateTimeToInstant(transactionContract.getTimestamp()));
    }

    @Test
    void contractToEntityShouldMapFieldsCorrectly() {
        // Given
        Instant now = Instant.now();
        OffsetDateTime nowOffsetDateTime = instantToOffsetDateTime(now);

        Transaction transactionContract = new Transaction();
        transactionContract.setId(1);
        transactionContract.setUserId(100);
        transactionContract.setType(TransactionType.INCOME);
        transactionContract.setValue(500);
        transactionContract.setDescription("Salary");
        transactionContract.setAccountId(10);
        transactionContract.setTimestamp(nowOffsetDateTime);

        // When
        TransactionEntity transactionEntity = TransactionContractMapper.contractToEntity(transactionContract);

        // Then
        assertNotNull(transactionEntity);
        assertEquals(1, transactionEntity.getId());
        assertEquals(100, transactionEntity.getUserId());
        assertEquals(TransactionType.INCOME, transactionEntity.getType());
        assertEquals(500, transactionEntity.getValue());
        assertEquals("Salary", transactionEntity.getDescription());
        assertEquals(10, transactionEntity.getAccountId());
        assertEquals(nowOffsetDateTime, transactionEntity.getTimestamp());
    }
}
