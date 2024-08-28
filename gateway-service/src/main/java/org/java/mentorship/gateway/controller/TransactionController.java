package org.java.mentorship.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.budget.client.TransactionFeignClient;
import org.java.mentorship.contracts.budget.dto.Transaction;
import org.java.mentorship.gateway.security.authorization.UserAuthorization;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionFeignClient transactionFeignClient;

    @GetMapping()
    ResponseEntity<List<Transaction>> getTransactions() {
        // TODO: Add any necessary restrictions or filters for retrieving transactions
        return ResponseEntity.ok(transactionFeignClient.getTransactions());
    }

    @GetMapping("/{id}")
    ResponseEntity<Transaction> getTransactionById(@PathVariable(name = "id") Integer id) {
        // TODO: Add any necessary authorization checks or validations
        return ResponseEntity.ok(transactionFeignClient.getTransactionById(id));
    }

    @PostMapping()
    ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        UserAuthorization.loggedInAsUser(transaction.getUserId());
        return ResponseEntity.ok(transactionFeignClient.createTransaction(transaction));
    }

    @PutMapping("/{id}")
    ResponseEntity<Transaction> updateTransaction(@PathVariable(name = "id") Integer id, @RequestBody Transaction transaction) {
        UserAuthorization.loggedInAsUser(transaction.getUserId());
        return ResponseEntity.ok(transactionFeignClient.updateTransaction(id, transaction));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTransaction(@PathVariable(name = "id") Integer id) {
        // TODO: Add any necessary checks or processing before deleting the transaction
        transactionFeignClient.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
