package org.java.mentorship.budget.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.domain.BudgetEntity;
import org.java.mentorship.budget.exception.NoEntityFoundException;
import org.java.mentorship.budget.persistence.BudgetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository repository;

    @Transactional
    public BudgetEntity save(final BudgetEntity budgetEntity) {
        return repository.save(budgetEntity);
    }

    public List<BudgetEntity> findAll() {
        return repository.findAll();
    }

    public BudgetEntity findById(final Integer id) {
        BudgetEntity budgetEntity = repository.findById(id);
        if (budgetEntity == null) {
            throw new NoEntityFoundException("Budget with id " + id + " not found");
        }
        return budgetEntity;
    }

    @Transactional
    public BudgetEntity update(final BudgetEntity budgetEntity) {
        BudgetEntity existingBudget = repository.findById(budgetEntity.getId());
        if (existingBudget == null) {
            throw new NoEntityFoundException("Budget with id " + budgetEntity.getId() + " not found");
        }
        return repository.update(budgetEntity);
    }

    @Transactional
    public BudgetEntity delete(final Integer id) {
        BudgetEntity budgetEntity = repository.findById(id);
        if (budgetEntity == null) {
            throw new NoEntityFoundException("Budget with id " + id + " not found");
        }
        repository.delete(id);
        return budgetEntity;
    }

    @Transactional
    public List<BudgetEntity> findByUserId(final Integer userId) {
        List<BudgetEntity> budgets = repository.findByUserId(userId);
        if (budgets.isEmpty()) {
            throw new NoEntityFoundException("No budgets found for user with id " + userId);
        }
        return budgets;
    }

}
