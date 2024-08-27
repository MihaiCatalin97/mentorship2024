package org.java.mentorship.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.budget.client.BudgetFeignClient;
import org.java.mentorship.contracts.budget.dto.Budget;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetFeignClient budgetFeignClient;

    @GetMapping
    ResponseEntity<List<Budget>> getBudgets(@RequestParam(required = false, name = "userId") Integer userId) {
        // TODO: Add any necessary restrictions or filters for retrieving budgets
        return ResponseEntity.ok(budgetFeignClient.getBudgets(userId));
    }

    @GetMapping("/{id}")
    ResponseEntity<Budget> getBudgetById(@PathVariable(name = "id") Integer id) {
        // TODO: Add any necessary authorization checks or validations
        return ResponseEntity.ok(budgetFeignClient.getBudgetById(id));
    }

    @PostMapping()
    ResponseEntity<Budget> createBudget(@RequestBody Budget budget) {
        // TODO: Add any necessary validation or pre-processing before creating the budget
        return ResponseEntity.ok(budgetFeignClient.createBudget(budget));
    }

    @PutMapping("/{id}")
    ResponseEntity<Budget> updateBudget(@PathVariable(name = "id") Integer id, @RequestBody Budget budget) {
        // TODO: Add any necessary validation or pre-processing before updating the budget
        return ResponseEntity.ok(budgetFeignClient.updateBudget(id, budget));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteBudget(@PathVariable(name = "id") Integer id) {
        // TODO: Add any necessary checks or processing before deleting the budget
        budgetFeignClient.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}
