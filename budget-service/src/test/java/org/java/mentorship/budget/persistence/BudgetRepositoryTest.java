package org.java.mentorship.budget.persistence;

import org.h2.tools.Server;
import org.java.mentorship.budget.domain.BudgetEntity;
import org.java.mentorship.budget.persistence.mapper.BudgetRowMapper;
import org.java.mentorship.contracts.budget.dto.BudgetInterval;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@ContextConfiguration(classes = {
        BudgetRepository.class,
        BudgetRowMapper.class
})
@Sql({
        "classpath:sql/schema.sql",
        "classpath:sql/testData.sql"
})
class BudgetRepositoryTest {

    @Autowired
    private BudgetRepository budgetRepository;

    @BeforeAll
    public static void initTest() throws SQLException {
        // Start the H2 web server to make the database accessible via browser for debugging
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8086")
                .start();
    }

    @Test
    void findAllShouldReturnAllDatabaseEntries() {
        // When
        List<BudgetEntity> results = budgetRepository.findAll();

        // Then
        assertEquals(2, results.size());  // Adjust the expected size based on test data
    }

    @Test
    void saveShouldInsertIntoTheDatabase() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setUserId(1);
        budget.setName("New Monthly Budget");
        budget.setMaximumAllowed(2000);
        budget.setInterval(BudgetInterval.MONTHLY); // Assuming you have a BudgetInterval enum
        budget.setTransactionId(1);
        budget.setAccountId(1);

        // When
        budgetRepository.save(budget);

        // Then
        List<BudgetEntity> result = budgetRepository.findAll();
        assertEquals(3, result.size());  // Adjust the expected size based on test data
        assertTrue(result.stream().anyMatch(b -> "New Monthly Budget".equals(b.getName())));
    }

    @Test
    void findByIdShouldReturnCorrectBudget() {
        // Given
        BudgetEntity budget = budgetRepository.findAll().get(0);

        // When
        BudgetEntity result = budgetRepository.findById(budget.getId());

        // Then
        assertNotNull(result);
        assertEquals(budget.getId(), result.getId());
        assertEquals(budget.getName(), result.getName());
        assertEquals(budget.getUserId(), result.getUserId());
    }

    @Test
    void updateShouldModifyExistingRecord() {
        // Given
        BudgetEntity budget = budgetRepository.findAll().get(0);
        budget.setName("Updated Budget Name");
        budget.setMaximumAllowed(3000);

        // When
        budgetRepository.update(budget);

        // Then
        BudgetEntity updatedBudget = budgetRepository.findById(budget.getId());
        assertEquals("Updated Budget Name", updatedBudget.getName());
        assertEquals(3000, updatedBudget.getMaximumAllowed());
    }

    @Test
    void deleteShouldRemoveRecordFromDatabase() {
        // Given
        BudgetEntity budget = budgetRepository.findAll().get(1);

        // When
        budgetRepository.delete(budget.getId());

        // Then
        List<BudgetEntity> results = budgetRepository.findAll();
        assertEquals(1, results.size());  // Adjust the expected size based on test data
        assertFalse(results.stream().anyMatch(b -> b.getId().equals(budget.getId())));
    }
}
