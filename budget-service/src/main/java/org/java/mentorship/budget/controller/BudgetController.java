package org.java.mentorship.budget.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.service.BudgetService;
import org.java.mentorship.contracts.budget.dto.Budget;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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
        Optional<Budget> budget = budgetService.getBudgetById(id);
        return budget.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
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
        Optional<Budget> budget = budgetService.updateBudget(id, updatedBudget);
        return budget.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    // Delete a budget by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Budget> deleteBudget(@PathVariable("id") Integer id) {
        Optional<Budget> deletedBudget = budgetService.deleteBudget(id);
        return deletedBudget.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    // Get all budgets for a specific user
    @GetMapping(params = "userId")
    public ResponseEntity<List<Budget>> getBudgetsByUserId(@RequestParam("userId") Integer userId) {
        List<Budget> userBudgets = budgetService.getBudgetsByUserId(userId);
        return ResponseEntity.ok(userBudgets);
    }
}
