package org.java.mentorship.budget.controller;

import org.java.mentorship.budget.domain.BudgetEntity;
import org.java.mentorship.budget.domain.mapper.BudgetContractMapper;
import org.java.mentorship.budget.service.BudgetService;
import org.java.mentorship.contracts.budget.dto.Budget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BudgetControllerTest {

    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private BudgetController budgetController;

    private List<BudgetEntity> budgetEntities;

    @BeforeEach
    void setupMocks() {
        budgetEntities = Arrays.asList(
                BudgetEntity.builder().id(1).name("Budget 1").build(),
                BudgetEntity.builder().id(2).name("Budget 2").build()
        );
    }

    @Test
    void getAllBudgetsShouldReturnContractsFromBudgetService() {
        when(budgetService.findAll()).thenReturn(budgetEntities);

        List<Budget> budgets = budgetEntities.stream()
                .map(BudgetContractMapper::entityToContract)
                .toList();

        ResponseEntity<List<Budget>> response = budgetController.getAllBudgets();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(budgets.get(0).getId(), response.getBody().get(0).getId());
    }

    @Test
    void getBudgetByIdShouldReturnContractFromBudgetService() {
        when(budgetService.findById(anyInt())).thenReturn(budgetEntities.get(0));

        ResponseEntity<Budget> response = budgetController.getBudgetById(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Budget 1", response.getBody().getName());
    }

    @Test
    void createBudgetShouldReturnCreatedBudget() {
        Budget budget = Budget.builder().name("New Budget").build();
        BudgetEntity budgetEntity = BudgetContractMapper.contractToEntity(budget);

        when(budgetService.save(any(BudgetEntity.class)))
                .thenReturn(BudgetEntity.builder().id(1).name("New Budget").build());

        ResponseEntity<Budget> response = budgetController.createBudget(budget);

        assertEquals(202, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("New Budget", response.getBody().getName());
    }

    @Test
    void updateBudgetShouldReturnUpdatedBudget() {
        Budget budget = Budget.builder().name("Updated Budget").build();
        BudgetEntity budgetEntity = BudgetContractMapper.contractToEntity(budget);
        budgetEntity.setId(1);

        when(budgetService.update(any(BudgetEntity.class)))
                .thenReturn(BudgetEntity.builder().id(1).name("Updated Budget").build());

        ResponseEntity<Budget> response = budgetController.updateBudget(1, budget);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Updated Budget", response.getBody().getName());
    }

    @Test
    void deleteBudgetShouldReturnDeletedBudget() {
        when(budgetService.delete(anyInt()))
                .thenReturn(BudgetEntity.builder().id(1).name("Deleted Budget").build());

        ResponseEntity<Budget> response = budgetController.deleteBudget(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Deleted Budget", response.getBody().getName());
    }

    @Test
    void getBudgetByIdShouldReturn404WhenBudgetNotFound() {
        when(budgetService.findById(anyInt())).thenThrow(new IllegalArgumentException("Budget not found"));

        assertThrows(IllegalArgumentException.class, () -> budgetController.getBudgetById(999));
    }

    @Test
    void deleteBudgetShouldReturn404WhenBudgetNotFound() {
        when(budgetService.delete(anyInt())).thenThrow(new IllegalArgumentException("Budget not found"));

        assertThrows(IllegalArgumentException.class, () -> budgetController.deleteBudget(999));
    }

    @Test
    void getBudgetsByUserIdShouldReturnBudgetsWhenBudgetsExist() {
        Integer userId = 1;
        List<BudgetEntity> budgetEntities = Arrays.asList(
                BudgetEntity.builder().id(1).name("Monthly Budget").build(),
                BudgetEntity.builder().id(2).name("Savings Goal").build()
        );

        List<Budget> expectedBudgets = budgetEntities.stream()
                .map(BudgetContractMapper::entityToContract)
                .collect(Collectors.toList());

        when(budgetService.findByUserId(userId)).thenReturn(budgetEntities);

        ResponseEntity<List<Budget>> response = budgetController.getBudgetsByUserId(userId);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(expectedBudgets.size(), response.getBody().size());
        for (int i = 0; i < expectedBudgets.size(); i++) {
            assertEquals(expectedBudgets.get(i).getId(), response.getBody().get(i).getId());
            assertEquals(expectedBudgets.get(i).getName(), response.getBody().get(i).getName());
        }
    }

    @Test
    void getBudgetsByUserIdShouldReturnEmptyListWhenNoBudgetsExist() {
        Integer userId = 999;

        when(budgetService.findByUserId(userId)).thenReturn(Arrays.asList());

        ResponseEntity<List<Budget>> response = budgetController.getBudgetsByUserId(userId);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }
}
