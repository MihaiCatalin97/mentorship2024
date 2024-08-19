package org.java.mentorship.budget.domain.mapper;

import org.java.mentorship.budget.domain.BudgetEntity;
import org.java.mentorship.contracts.budget.dto.Budget;
import org.java.mentorship.contracts.budget.dto.BudgetInterval;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BudgetContractMapperTest {

    @Test
    void entityToContractShouldMapFieldsCorrectly() {
        // Given
        BudgetEntity budgetEntity = new BudgetEntity();
        budgetEntity.setId(1);
        budgetEntity.setUserId(100);
        budgetEntity.setName("Annual Budget");
        budgetEntity.setMaximumAllowed(5000);
        budgetEntity.setInterval(BudgetInterval.MONTHLY);
        budgetEntity.setCurrentUsage(1200);
        budgetEntity.setTransactionId(200);
        budgetEntity.setAccountId(300);

        // When
        Budget budgetContract = BudgetContractMapper.entityToContract(budgetEntity);

        // Then
        assertNotNull(budgetContract);
        assertEquals(1, budgetContract.getId());
        assertEquals(100, budgetContract.getUserId());
        assertEquals("Annual Budget", budgetContract.getName());
        assertEquals(5000, budgetContract.getMaximumAllowed());
        assertEquals(BudgetInterval.MONTHLY, budgetContract.getInterval());
        assertEquals(1200, budgetContract.getCurrentUsage());
        assertEquals(200, budgetContract.getTransactionId());
        assertEquals(300, budgetContract.getAccountId());
    }

    @Test
    void contractToEntityShouldMapFieldsCorrectly() {
        // Given
        Budget budgetContract = new Budget();
        budgetContract.setId(1);
        budgetContract.setUserId(100);
        budgetContract.setName("Annual Budget");
        budgetContract.setMaximumAllowed(5000);
        budgetContract.setInterval(BudgetInterval.MONTHLY);
        budgetContract.setCurrentUsage(1200);
        budgetContract.setTransactionId(200);
        budgetContract.setAccountId(300);

        // When
        BudgetEntity budgetEntity = BudgetContractMapper.contractToEntity(budgetContract);

        // Then
        assertNotNull(budgetEntity);
        assertEquals(1, budgetEntity.getId());
        assertEquals(100, budgetEntity.getUserId());
        assertEquals("Annual Budget", budgetEntity.getName());
        assertEquals(5000, budgetEntity.getMaximumAllowed());
        assertEquals(BudgetInterval.MONTHLY, budgetEntity.getInterval());
        assertEquals(1200, budgetEntity.getCurrentUsage());
        assertEquals(200, budgetEntity.getTransactionId());
        assertEquals(300, budgetEntity.getAccountId());
    }
}
