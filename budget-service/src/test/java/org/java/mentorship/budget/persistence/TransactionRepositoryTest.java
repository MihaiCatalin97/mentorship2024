package org.java.mentorship.budget.persistence;

import org.h2.tools.Server;
import org.java.mentorship.budget.domain.TransactionEntity;
import org.java.mentorship.budget.persistence.mapper.TransactionRowMapper;
import org.java.mentorship.contracts.budget.dto.TransactionType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@ContextConfiguration(classes = {
        TransactionRepository.class,
        TransactionRowMapper.class
})
@Sql({
        "classpath:sql/schema.sql",
        "classpath:sql/testData.sql"
})
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeAll
    public static void initTest() throws SQLException {
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8083")
                .start();
    }

    @Test
    void findAllShouldReturnAllDatabaseEntries() {
        // When
        List<TransactionEntity> results = transactionRepository.findAll();

        // Then
        assertEquals(2, results.size());  // Adjust the expected size based on test data
    }

    @Test
    void saveShouldInsertIntoTheDatabase() {
        // Given
        TransactionEntity transaction = new TransactionEntity();
        transaction.setUserId(1);
        transaction.setType(TransactionType.INCOME); // Using the TransactionType enum
        transaction.setValue(1000);
        transaction.setDescription("New income transaction");
        transaction.setAccountId(1);
        transaction.setTimestamp(OffsetDateTime.now()); // Use OffsetDateTime instead of Timestamp

        // When
        transactionRepository.save(transaction);

        // Then
        List<TransactionEntity> result = transactionRepository.findAll();
        assertEquals(3, result.size());  // Adjust the expected size based on test data
        assertTrue(result.stream().anyMatch(t -> "New income transaction".equals(t.getDescription())));
    }

    @Test
    void findByIdShouldReturnCorrectTransaction() {
        // Given
        TransactionEntity transaction = transactionRepository.findAll().get(0);

        // When
        TransactionEntity result = transactionRepository.findById(transaction.getId());

        // Then
        assertNotNull(result);
        assertEquals(transaction.getId(), result.getId());
        assertEquals(transaction.getDescription(), result.getDescription());
        assertEquals(transaction.getUserId(), result.getUserId());
    }

    @Test
    void updateShouldModifyExistingRecord() {
        // Given
        TransactionEntity transaction = transactionRepository.findAll().get(0);
        transaction.setDescription("Updated income transaction");
        transaction.setValue(2000);

        // When
        transactionRepository.update(transaction);

        // Then
        TransactionEntity updatedTransaction = transactionRepository.findById(transaction.getId());
        assertEquals("Updated income transaction", updatedTransaction.getDescription());
        assertEquals(2000, updatedTransaction.getValue());
    }

    @Test
    void deleteShouldRemoveRecordFromDatabase() {
        // Given
        TransactionEntity transaction = transactionRepository.findAll().get(0);

        // When
        transactionRepository.delete(transaction.getId());

        // Then
        List<TransactionEntity> results = transactionRepository.findAll();
        assertEquals(1, results.size());  // Adjust the expected size based on test data
        assertFalse(results.stream().anyMatch(t -> t.getId().equals(transaction.getId())));
    }
}
