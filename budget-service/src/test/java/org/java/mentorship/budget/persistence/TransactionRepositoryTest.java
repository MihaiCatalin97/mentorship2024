package org.java.mentorship.budget.persistence;

import org.h2.tools.Server;
import org.java.mentorship.budget.domain.TransactionEntity;
import org.java.mentorship.budget.persistence.mapper.TransactionRowMapper;
import org.java.mentorship.contracts.budget.dto.TransactionType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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
    static void initTest() throws SQLException {
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8083")
                .start();
    }

    @BeforeEach
    void setup() {
    }

    @Test
    void findAllShouldReturnAllDatabaseEntries() {
        // When
        List<TransactionEntity> results = transactionRepository.findAll();

        // Then
        assertNotNull(results, "Results should not be null");
        assertEquals(2, results.size(), "Should return 2 transactions from the database");
    }

    @Test
    void saveShouldInsertIntoTheDatabase() {
        // Given
        TransactionEntity transaction = new TransactionEntity();
        transaction.setUserId(1);
        transaction.setType(TransactionType.INCOME);
        transaction.setValue(1000);
        transaction.setDescription("New income transaction");
        transaction.setCategoryId(1);
        transaction.setAccountId(1);
        transaction.setTimestamp(OffsetDateTime.now());

        // When
        transactionRepository.save(transaction);

        // Then
        List<TransactionEntity> result = transactionRepository.findAll();
        assertNotNull(result, "Result should not be null");
        assertEquals(3, result.size(), "Should return 3 transactions after insert");
        assertTrue(result.stream().anyMatch(t -> "New income transaction".equals(t.getDescription())),
                "Transaction with description 'New income transaction' should be present");
    }

    @Test
    void findByIdShouldReturnCorrectTransaction() {
        // Given
        TransactionEntity transaction = transactionRepository.findAll().get(0);

        // When
        TransactionEntity result = transactionRepository.findById(transaction.getId());

        // Then
        assertNotNull(result, "Result should not be null");
        assertEquals(transaction.getId(), result.getId(), "Transaction ID should match");
        assertEquals(transaction.getDescription(), result.getDescription(), "Descriptions should match");
        assertEquals(transaction.getUserId(), result.getUserId(), "User IDs should match");
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
        assertNotNull(updatedTransaction, "Updated transaction should not be null");
        assertEquals("Updated income transaction", updatedTransaction.getDescription(), "Description should be updated");
        assertEquals(2000, updatedTransaction.getValue(), "Value should be updated");
    }

    @Test
    void deleteShouldRemoveRecordFromDatabase() {
        // Given
        TransactionEntity transaction = transactionRepository.findAll().get(1);

        // When
        TransactionEntity deletedTransaction = transactionRepository.delete(transaction.getId());

        // Then
        List<TransactionEntity> results = transactionRepository.findAll();
        assertNotNull(results, "Results should not be null");
        assertEquals(1, results.size(), "Should return 1 transaction after deletion");
        assertFalse(results.stream().anyMatch(t -> t.getId().equals(deletedTransaction.getId())),
                "Deleted transaction should not be present in the results");
    }

    @Test
    void findTransactionsFromLast7DaysShouldReturnRecentTransactions() {
        // Given
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        TransactionEntity recentTransaction = TransactionEntity.builder()
                .id(3)
                .userId(1)
                .type(TransactionType.INCOME)
                .value(500)
                .description("Recent transaction")
                .categoryId(1)
                .accountId(1)
                .timestamp(now)
                .build();
        transactionRepository.save(recentTransaction);

        TransactionEntity oldTransaction = TransactionEntity.builder()
                .id(4)
                .userId(1)
                .type(TransactionType.EXPENSE)
                .value(-100)
                .description("Old transaction")
                .categoryId(1)
                .accountId(1)
                .timestamp(now.minusDays(10))
                .build();
        transactionRepository.save(oldTransaction);

        // When
        List<TransactionEntity> recentTransactions = transactionRepository.findTransactionsFromLast7Days();

        // Then
        assertNotNull(recentTransactions, "Recent transactions should not be null");
        assertEquals(3, recentTransactions.size(), "Should return 1 recent transaction");
        assertEquals(1, recentTransactions.get(0).getId(), "Transaction ID should match");
        assertEquals("Initial deposit", recentTransactions.get(0).getDescription(), "Transaction description should match");
    }

    @Test
    void findTransactionsFromLast7DaysShouldReturnEmptyWhenNoRecentTransactions() {
        // Given
        TransactionEntity oldTransaction = TransactionEntity.builder()
                .id(5)
                .userId(1)
                .type(TransactionType.EXPENSE)
                .value(-200)
                .description("Older transaction")
                .categoryId(1)
                .accountId(1)
                .timestamp(OffsetDateTime.now(ZoneOffset.UTC).minusDays(10))
                .build();
        transactionRepository.save(oldTransaction);

        // When
        List<TransactionEntity> recentTransactions = transactionRepository.findTransactionsFromLast7Days();

        // Then
        assertNotNull(recentTransactions, "Recent transactions should not be null");
        assertFalse(recentTransactions.isEmpty(), "Should return no recent transactions");
    }
}
