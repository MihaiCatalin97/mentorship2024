package org.java.mentorship.gateway.controller;

import org.java.mentorship.contracts.budget.client.BudgetFeignClient;
import org.java.mentorship.contracts.budget.dto.Budget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BudgetControllerTest {

    @Mock
    private BudgetFeignClient budgetFeignClient;

    @InjectMocks
    private BudgetController budgetController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBudgets_ShouldReturnListOfBudgets() {
        List<Budget> mockBudgets = Arrays.asList(
                new Budget(1, 1, "Budget 1", 1000, null, 1, 1),
                new Budget(2, 2, "Budget 2", 2000, null, 2, 2)
        );
        when(budgetFeignClient.getBudgets()).thenReturn(mockBudgets);

        ResponseEntity<List<Budget>> response = budgetController.getBudgets();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBudgets, response.getBody());
        verify(budgetFeignClient, times(1)).getBudgets();
    }

    @Test
    void getBudgetById_ShouldReturnBudget_WhenBudgetExists() {
        Budget mockBudget = new Budget(1, 1, "Budget 1", 1000, null, 1, 1);
        when(budgetFeignClient.getBudgetById(1)).thenReturn(mockBudget);

        ResponseEntity<Budget> response = budgetController.getBudgetById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBudget, response.getBody());
        verify(budgetFeignClient, times(1)).getBudgetById(1);
    }

    @Test
    void createBudget_ShouldReturnCreatedBudget() {
        Budget budgetToCreate = new Budget(null, 1, "Budget 1", 1000, null, 1, 1);
        Budget createdBudget = new Budget(1, 1, "Budget 1", 1000, null, 1, 1);
        when(budgetFeignClient.createBudget(any(Budget.class))).thenReturn(createdBudget);

        ResponseEntity<Budget> response = budgetController.createBudget(budgetToCreate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdBudget, response.getBody());
        verify(budgetFeignClient, times(1)).createBudget(budgetToCreate);
    }

    @Test
    void updateBudget_ShouldReturnUpdatedBudget() {
        Budget budgetToUpdate = new Budget(1, 1, "Updated Budget", 1500, null, 1, 1);
        when(budgetFeignClient.updateBudget(eq(1), any(Budget.class))).thenReturn(budgetToUpdate);

        ResponseEntity<Budget> response = budgetController.updateBudget(1, budgetToUpdate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(budgetToUpdate, response.getBody());
        verify(budgetFeignClient, times(1)).updateBudget(1, budgetToUpdate);
    }

    @Test
    void deleteBudget_ShouldReturnNoContent() {
        ResponseEntity<Void> response = budgetController.deleteBudget(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(budgetFeignClient, times(1)).deleteBudget(1);
    }
}
