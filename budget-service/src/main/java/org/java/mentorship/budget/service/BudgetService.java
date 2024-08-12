package org.java.mentorship.budget.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.exception.NoEntityFoundException;
import org.java.mentorship.budget.repository.BudgetRepository;
import org.java.mentorship.budget.validation.BudgetValidator;
import org.java.mentorship.contracts.budget.dto.Budget;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetValidator budgetValidator;

    public List<Budget> getBudgets() {
        return budgetRepository.findAll();
    }

    public Budget getBudgetById(Integer id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("Budget with id " + id + " not found"));
    }

    public Budget createBudget(Budget budget) {
        budgetValidator.validate(budget);
        return budgetRepository.save(budget);
    }

    public Budget updateBudget(Integer id, Budget updatedBudget) {
        budgetValidator.validate(updatedBudget);
        Budget existingBudget = budgetRepository.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("Budget with id " + id + " not found"));

        updatedBudget.setId(id);
        return budgetRepository.save(updatedBudget);
    }

    public Budget deleteBudget(Integer id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("Budget with id " + id + " not found"));

        budgetRepository.deleteById(id);
        return budget;
    }

    public List<Budget> getBudgetsByUserId(Integer userId) {
        return budgetRepository.findByUserId(userId);
    }
}
