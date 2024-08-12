package org.java.mentorship.budget.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.repository.BudgetRepository;
import org.java.mentorship.contracts.budget.dto.Budget;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public List<Budget> getBudgets() {
        return budgetRepository.findAll();
    }

    public Optional<Budget> getBudgetById(Integer id) {
        return budgetRepository.findById(id);
    }

    public Budget createBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public Optional<Budget> updateBudget(Integer id, Budget updatedBudget) {
        return budgetRepository.findById(id).map(existingBudget -> {
            updatedBudget.setId(id);
            return budgetRepository.save(updatedBudget);
        });
    }

    public Optional<Budget> deleteBudget(Integer id) {
        Optional<Budget> budget = budgetRepository.findById(id);
        if (budget.isPresent()) {
            budgetRepository.deleteById(id);
        }
        return budget;
    }

    public List<Budget> getBudgetsByUserId(Integer userId) {
        return budgetRepository.findByUserId(userId);
    }
}
