package org.java.mentorship.budget.repository;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.budget.dto.Budget;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BudgetRepository {

    private final List<Budget> budgets = new ArrayList<>();
    private Integer currentId = 0;

    public List<Budget> findAll() {
        return new ArrayList<>(budgets);
    }

    public Optional<Budget> findById(Integer id) {
        return budgets.stream()
                .filter(budget -> budget.getId().equals(id))
                .findFirst();
    }

    public Budget save(Budget budget) {
        budget.setId(++currentId);
        budgets.add(budget);
        return budget;
    }

    public boolean deleteById(Integer id) {
        return budgets.removeIf(budget -> budget.getId().equals(id));
    }

    public List<Budget> findByUserId(Integer userId) {
        List<Budget> userBudgets = new ArrayList<>();
        for (Budget budget : budgets) {
            if (budget.getUserId().equals(userId)) {
                userBudgets.add(budget);
            }
        }
        return userBudgets;
    }
}
