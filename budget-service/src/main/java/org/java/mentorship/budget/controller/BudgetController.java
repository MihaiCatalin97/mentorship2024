package org.java.mentorship.budget.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.service.BudgetService;
import org.java.mentorship.contracts.budget.dto.Budget;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    // Get all budgets
    @GetMapping
    public ResponseEntity<List<Budget>> getBudgets() {
        List<Budget> budgets = budgetService.getBudgets();
        return ResponseEntity.ok(budgets);
    }

    // Get a budget by ID
    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable("id") Integer id) {
        Budget budget = budgetService.getBudgetById(id);
        return ResponseEntity.ok(budget);
    }

    // Create a new budget
    @PostMapping
    public ResponseEntity<Budget> createBudget(@RequestBody Budget budget) {
        Budget createdBudget = budgetService.createBudget(budget);
        return ResponseEntity.status(CREATED).body(createdBudget);
    }

    // Update an existing budget by ID
    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable("id") Integer id, @RequestBody Budget updatedBudget) {
        Budget updated = budgetService.updateBudget(id, updatedBudget);
        return ResponseEntity.ok(updated);
    }

    // Delete a budget by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Budget> deleteBudget(@PathVariable("id") Integer id) {
        Budget deletedBudget = budgetService.deleteBudget(id);
        return ResponseEntity.ok(deletedBudget);
    }

    // Get all budgets for a specific user
    @GetMapping(params = "userId")
    public ResponseEntity<List<Budget>> getBudgetsByUserId(@RequestParam("userId") Integer userId) {
        List<Budget> userBudgets = budgetService.getBudgetsByUserId(userId);
        return ResponseEntity.ok(userBudgets);
    }
}
