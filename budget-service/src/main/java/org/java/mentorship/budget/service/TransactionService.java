package org.java.mentorship.budget.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.domain.TransactionEntity;
import org.java.mentorship.budget.exception.NoEntityFoundException;
import org.java.mentorship.budget.persistence.TransactionRepository;
import org.springframework.dao.EmptyResultDataAccessException;
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
        try {
            return repository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoEntityFoundException("Transaction with id " + id + " not found");
        }
    }

    public TransactionEntity update(final TransactionEntity transactionEntity) {
        try {
            TransactionEntity existingTransaction = repository.findById(transactionEntity.getId());
            return repository.update(transactionEntity);
        } catch (EmptyResultDataAccessException e) {
            throw new NoEntityFoundException("Transaction with id " + transactionEntity.getId() + " not found");
        }
    }

    public TransactionEntity delete(final Integer id) {
        try {
            TransactionEntity transactionEntity = repository.findById(id);
            repository.delete(id);
            return transactionEntity;
        } catch (EmptyResultDataAccessException e) {
            throw new NoEntityFoundException("Transaction with id " + id + " not found");
        }
    }
}
