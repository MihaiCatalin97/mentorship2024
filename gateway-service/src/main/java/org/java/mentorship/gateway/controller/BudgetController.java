package org.java.mentorship.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.budget.client.BudgetFeignClient;
import org.java.mentorship.contracts.budget.dto.Budget;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.java.mentorship.gateway.security.authorization.UserAuthorization.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetFeignClient budgetFeignClient;

    @GetMapping()
    ResponseEntity<List<Budget>> getBudgets() {
        loggedInAsAnyUser();

        return ResponseEntity.ok(filterResults(budgetFeignClient.getBudgets(), Budget::getUserId));
    }

    @GetMapping("/{id}")
    ResponseEntity<Budget> getBudgetById(@PathVariable(name = "id") Integer id) {
        loggedInAsAnyUser();

        Budget budget = budgetFeignClient.getBudgetById(id);

        return filterResult(budget, Budget::getUserId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    ResponseEntity<Budget> createBudget(@RequestBody Budget budget) {
        loggedInAsUser(budget.getUserId());

        return ResponseEntity.ok(budgetFeignClient.createBudget(budget));
    }

    @PutMapping("/{id}")
    ResponseEntity<Budget> updateBudget(@PathVariable(name = "id") Integer id, @RequestBody Budget budget) {
        loggedInAsUser(budget.getUserId());

        return ResponseEntity.ok(budgetFeignClient.updateBudget(id, budget));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteBudget(@PathVariable(name = "id") Integer id) {
        Budget budget = budgetFeignClient.getBudgetById(id);

        loggedInAsUser(budget.getUserId());
        budgetFeignClient.deleteBudget(id);

        return ResponseEntity.noContent().build();
    }
}
