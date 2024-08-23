package org.java.mentorship.budget.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.java.mentorship.budget.domain.TransactionEntity;
import org.java.mentorship.budget.domain.mapper.TransactionContractMapper;
import org.java.mentorship.budget.service.TransactionService;
import org.java.mentorship.contracts.budget.dto.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAll().stream()
                .map(TransactionContractMapper::entityToContract)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactions);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") Integer id) {
        TransactionEntity transactionEntity = transactionService.findById(id);
        Transaction transaction = TransactionContractMapper.entityToContract(transactionEntity);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody @Valid Transaction transaction) {
        TransactionEntity transactionEntity = TransactionContractMapper.contractToEntity(transaction);
        TransactionEntity savedTransaction = transactionService.save(transactionEntity);
        Transaction createdTransaction = TransactionContractMapper.entityToContract(savedTransaction);
        return ResponseEntity.status(ACCEPTED).body(createdTransaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable("id") Integer id,
                                                         @RequestBody @Valid Transaction transaction) {
        TransactionEntity transactionEntity = TransactionContractMapper.contractToEntity(transaction);
        transactionEntity.setId(id);
        TransactionEntity updatedTransaction = transactionService.update(transactionEntity);
        Transaction updatedTransactionContract = TransactionContractMapper.entityToContract(updatedTransaction);
        return ResponseEntity.ok(updatedTransactionContract);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable("id") Integer id) {
        TransactionEntity deletedTransaction = transactionService.delete(id);
        Transaction transaction = TransactionContractMapper.entityToContract(deletedTransaction);
        return ResponseEntity.ok(transaction);
    }
}
