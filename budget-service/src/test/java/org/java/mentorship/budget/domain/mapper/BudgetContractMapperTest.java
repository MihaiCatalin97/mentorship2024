package org.java.mentorship.budget.domain.mapper;

import org.java.mentorship.budget.domain.BudgetEntity;
import org.java.mentorship.contracts.budget.dto.Budget;
import org.java.mentorship.contracts.budget.dto.BudgetInterval;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BudgetContractMapperTest {

    @Test
    void budgetContractMapperClassInstantiation() {
        BudgetContractMapper mapper = new BudgetContractMapper();
        assertNotNull(mapper);
    }

    @Test
    void entityToContractShouldMapFieldsCorrectly() {
        // Given
        BudgetEntity budgetEntity = BudgetEntity.builder()
                .id(1)
                .userId(100)
                .name("Annual Budget")
                .maximumAllowed(5000)
                .interval(BudgetInterval.MONTHLY)
                .categoryId(200)
                .accountId(300)
                .build();

        // When
        Budget budgetContract = BudgetContractMapper.entityToContract(budgetEntity);

        // Then
        assertNotNull(budgetContract);
        assertEquals(1, budgetContract.getId());
        assertEquals(100, budgetContract.getUserId());
        assertEquals("Annual Budget", budgetContract.getName());
        assertEquals(5000, budgetContract.getMaximumAllowed());
        assertEquals(BudgetInterval.MONTHLY, budgetContract.getInterval());
        assertEquals(200, budgetContract.getCategoryId());
        assertEquals(300, budgetContract.getAccountId());
    }

    @Test
    void contractToEntityShouldMapFieldsCorrectly() {
        // Given
        Budget budgetContract = Budget.builder()
                .id(1)
                .userId(100)
                .name("Annual Budget")
                .maximumAllowed(5000)
                .interval(BudgetInterval.MONTHLY)
                .categoryId(200)
                .accountId(300)
                .build();

        // When
        BudgetEntity budgetEntity = BudgetContractMapper.contractToEntity(budgetContract);

        // Then
        assertNotNull(budgetEntity);
        assertEquals(1, budgetEntity.getId());
        assertEquals(100, budgetEntity.getUserId());
        assertEquals("Annual Budget", budgetEntity.getName());
        assertEquals(5000, budgetEntity.getMaximumAllowed());
        assertEquals(BudgetInterval.MONTHLY, budgetEntity.getInterval());
        assertEquals(200, budgetEntity.getCategoryId());
        assertEquals(300, budgetEntity.getAccountId());
    }
}
