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
    static void initTest() throws SQLException {
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8086").start();
    }

    @Test
    void findAllShouldReturnAllDatabaseEntries() {
        // When
        List<BudgetEntity> results = budgetRepository.findAll();

        // Then
        assertEquals(2, results.size());
    }

    @Test
    void saveShouldInsertIntoTheDatabase() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setUserId(1);
        budget.setName("New Monthly Budget");
        budget.setMaximumAllowed(2000);
        budget.setInterval(BudgetInterval.MONTHLY);
        budget.setCategoryId(1);
        budget.setAccountId(1);

        // When
        budgetRepository.save(budget);

        // Then
        List<BudgetEntity> result = budgetRepository.findAll();
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(b -> "New Monthly Budget".equals(b.getName())));
    }

    @Test
    void findByIdShouldReturnCorrectBudget() {
        // Given
        List<BudgetEntity> allBudgets = budgetRepository.findAll();
        assertFalse(allBudgets.isEmpty(), "Test database is empty.");
        BudgetEntity budget = allBudgets.get(0);

        // When
        BudgetEntity result = budgetRepository.findById(budget.getId());

        // Then
        assertNotNull(result);
        assertEquals(budget.getId(), result.getId());
        assertEquals(budget.getName(), result.getName());
        assertEquals(budget.getUserId(), result.getUserId());
        assertEquals(budget.getMaximumAllowed(), result.getMaximumAllowed());
        assertEquals(budget.getInterval(), result.getInterval());
        assertEquals(budget.getCategoryId(), result.getCategoryId());
        assertEquals(budget.getAccountId(), result.getAccountId());
    }

    @Test
    void updateShouldModifyExistingRecord() {
        // Given
        List<BudgetEntity> allBudgets = budgetRepository.findAll();
        assertFalse(allBudgets.isEmpty(), "Test database is empty.");
        BudgetEntity budget = allBudgets.get(0);
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
        List<BudgetEntity> allBudgets = budgetRepository.findAll();
        assertFalse(allBudgets.isEmpty(), "Test database is empty.");
        BudgetEntity budget = allBudgets.get(1);

        // When
        BudgetEntity deletedBudget = budgetRepository.delete(budget.getId());

        // Then
        List<BudgetEntity> results = budgetRepository.findAll();
        assertEquals(1, results.size());
        assertFalse(results.stream().anyMatch(b -> b.getId().equals(deletedBudget.getId())));
    }

    @Test
    void findByUserIdShouldReturnBudgetsWhenBudgetsExist() {
        Integer userId = 1;
        List<BudgetEntity> expectedBudgets = budgetRepository.findByUserId(userId);

        List<BudgetEntity> result = budgetRepository.findByUserId(userId);

        assertEquals(expectedBudgets.size(), result.size());
        for (BudgetEntity expected : expectedBudgets) {
            assertTrue(result.stream().anyMatch(b ->
                    b.getId().equals(expected.getId()) &&
                            b.getUserId().equals(expected.getUserId()) &&
                            b.getName().equals(expected.getName()) &&
                            b.getMaximumAllowed().equals(expected.getMaximumAllowed()) &&
                            b.getInterval().equals(expected.getInterval()) &&
                            b.getCategoryId().equals(expected.getCategoryId()) &&
                            b.getAccountId().equals(expected.getAccountId())
            ));
        }
    }

    @Test
    void findByUserIdShouldReturnEmptyListWhenNoBudgetsExist() {
        Integer userId = 999;

        List<BudgetEntity> result = budgetRepository.findByUserId(userId);

        assertTrue(result.isEmpty());
    }
}
