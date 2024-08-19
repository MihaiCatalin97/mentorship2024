package org.java.mentorship.budget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.domain.BudgetEntity;
import org.java.mentorship.budget.domain.mapper.BudgetContractMapper;
import org.java.mentorship.budget.service.BudgetService;
import org.java.mentorship.contracts.budget.dto.Budget;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping
    public ResponseEntity<List<Budget>> getAllBudgets() {
        List<Budget> budgets = budgetService.findAll().stream()
                .map(BudgetContractMapper::entityToContract)
                .collect(Collectors.toList());
        return ResponseEntity.ok(budgets);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable("id") Integer id) {
        BudgetEntity budgetEntity = budgetService.findById(id);
        Budget budget = BudgetContractMapper.entityToContract(budgetEntity);
        return ResponseEntity.ok(budget);
    }

    @PostMapping
    public ResponseEntity<Budget> createBudget(@RequestBody @Valid Budget budget) {
        BudgetEntity budgetEntity = BudgetContractMapper.contractToEntity(budget);
        BudgetEntity savedBudget = budgetService.save(budgetEntity);
        Budget createdBudget = BudgetContractMapper.entityToContract(savedBudget);
        return ResponseEntity.status(ACCEPTED).body(createdBudget);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable("id") Integer id,
                                               @RequestBody @Valid Budget budget) {
        BudgetEntity budgetEntity = BudgetContractMapper.contractToEntity(budget);
        budgetEntity.setId(id);
        BudgetEntity updatedBudget = budgetService.update(budgetEntity);
        Budget updatedBudgetContract = BudgetContractMapper.entityToContract(updatedBudget);
        return ResponseEntity.ok(updatedBudgetContract);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Budget> deleteBudget(@PathVariable("id") Integer id) {
        BudgetEntity deletedBudget = budgetService.delete(id);
        Budget budget = BudgetContractMapper.entityToContract(deletedBudget);
        return ResponseEntity.ok(budget);
    }
}
