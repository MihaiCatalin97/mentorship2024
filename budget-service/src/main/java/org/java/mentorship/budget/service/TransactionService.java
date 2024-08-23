package org.java.mentorship.budget.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.domain.TransactionEntity;
import org.java.mentorship.budget.exception.NoEntityFoundException;
import org.java.mentorship.budget.persistence.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionEntity save(final TransactionEntity transactionEntity) {
        return repository.save(transactionEntity);
    }

    public List<TransactionEntity> findAll() {
        return repository.findAll();
    }

    public TransactionEntity findById(final Integer id) {
        TransactionEntity transactionEntity = repository.findById(id);
        if (transactionEntity == null) {
            throw new NoEntityFoundException("Transaction with id " + id + " not found");
        }
        return transactionEntity;
    }

    public TransactionEntity update(final TransactionEntity transactionEntity) {
        TransactionEntity existingTransaction = repository.findById(transactionEntity.getId());
        if (existingTransaction == null) {
            throw new NoEntityFoundException("Transaction with id " + transactionEntity.getId() + " not found");
        }
        return repository.update(transactionEntity);
    }

    public TransactionEntity delete(final Integer id) {
        TransactionEntity transactionEntity = repository.findById(id);
        if (transactionEntity == null) {
            throw new NoEntityFoundException("Transaction with id " + id + " not found");
        }
        repository.delete(id);
        return transactionEntity;
    }
}
