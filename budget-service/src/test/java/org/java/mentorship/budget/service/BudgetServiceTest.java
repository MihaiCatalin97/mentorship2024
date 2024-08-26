package org.java.mentorship.budget.service;

import org.java.mentorship.budget.domain.BudgetEntity;
import org.java.mentorship.budget.exception.NoEntityFoundException;
import org.java.mentorship.budget.persistence.BudgetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BudgetServiceTest {

    @InjectMocks
    private BudgetService budgetService;

    @Mock
    private BudgetRepository repository;

    @Test
    void saveShouldValidateAndSaveBudget() {
        BudgetEntity budgetEntity = new BudgetEntity();
        when(repository.save(any(BudgetEntity.class))).thenReturn(budgetEntity);

        BudgetEntity result = budgetService.save(budgetEntity);

        assertEquals(budgetEntity, result);
        verify(repository).save(budgetEntity);
    }

    @Test
    void findAllShouldCallRepository() {
        List<BudgetEntity> budgets = Arrays.asList(new BudgetEntity(), new BudgetEntity());
        when(repository.findAll()).thenReturn(budgets);

        List<BudgetEntity> result = budgetService.findAll();

        assertEquals(budgets, result);
        verify(repository).findAll();
    }

    @Test
    void findByIdShouldReturnBudgetWhenExists() {
        BudgetEntity budgetEntity = new BudgetEntity();
        when(repository.findById(1)).thenReturn(budgetEntity);

        BudgetEntity result = budgetService.findById(1);

        assertEquals(budgetEntity, result);
        verify(repository).findById(1);
    }

    @Test
    void findByIdShouldThrowExceptionWhenNotFound() {
        when(repository.findById(1)).thenReturn(null);

        assertThrows(NoEntityFoundException.class, () -> budgetService.findById(1));
        verify(repository).findById(1);
    }

    @Test
    void updateShouldValidateAndUpdateBudgetWhenExists() {
        BudgetEntity budgetEntity = new BudgetEntity();
        budgetEntity.setId(1);
        when(repository.findById(1)).thenReturn(budgetEntity);
        when(repository.update(any(BudgetEntity.class))).thenReturn(budgetEntity);

        BudgetEntity result = budgetService.update(budgetEntity);

        assertEquals(budgetEntity, result);
        verify(repository).findById(1);
        verify(repository).update(budgetEntity);
    }

    @Test
    void updateShouldThrowExceptionWhenBudgetNotFound() {
        BudgetEntity budgetEntity = new BudgetEntity();
        budgetEntity.setId(1);
        when(repository.findById(1)).thenReturn(null);

        assertThrows(NoEntityFoundException.class, () -> budgetService.update(budgetEntity));
        verify(repository).findById(1);
        verify(repository, never()).update(any(BudgetEntity.class));
    }

    @Test
    void deleteShouldReturnDeletedBudgetWhenExists() {
        BudgetEntity budgetEntity = new BudgetEntity();
        when(repository.findById(1)).thenReturn(budgetEntity);
        when(repository.delete(1)).thenReturn(budgetEntity);

        BudgetEntity result = budgetService.delete(1);

        assertEquals(budgetEntity, result);
        verify(repository).findById(1);
        verify(repository).delete(1);
    }

    @Test
    void deleteShouldThrowExceptionWhenBudgetNotFound() {
        when(repository.findById(1)).thenReturn(null);

        assertThrows(NoEntityFoundException.class, () -> budgetService.delete(1));
        verify(repository).findById(1);
        verify(repository, never()).delete(1);
    }

    @Test
    void findByUserIdShouldReturnBudgetsWhenBudgetsExist() {
        Integer userId = 1;
        BudgetEntity budget1 = new BudgetEntity();
        BudgetEntity budget2 = new BudgetEntity();
        List<BudgetEntity> budgets = Arrays.asList(budget1, budget2);
        when(repository.findByUserId(userId)).thenReturn(budgets);

        List<BudgetEntity> result = budgetService.findByUserId(userId);

        assertEquals(budgets, result);
        verify(repository).findByUserId(userId);
    }

    @Test
    void findByUserIdShouldThrowExceptionWhenNoBudgetsExist() {
        Integer userId = 1;
        when(repository.findByUserId(userId)).thenReturn(Arrays.asList());

        assertThrows(NoEntityFoundException.class, () -> budgetService.findByUserId(userId));
        verify(repository).findByUserId(userId);
    }
}
