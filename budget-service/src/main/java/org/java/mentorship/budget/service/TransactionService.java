package org.java.mentorship.budget.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.domain.BankAccountEntity;
import org.java.mentorship.budget.domain.TransactionEntity;
import org.java.mentorship.budget.exception.NoEntityFoundException;
import org.java.mentorship.budget.persistence.TransactionRepository;
import org.java.mentorship.contracts.budget.dto.TransactionType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final AccountService bankAccountService;

    @Transactional
    public TransactionEntity save(final TransactionEntity transactionEntity) {
        TransactionEntity savedTransaction = repository.save(transactionEntity);
        updateAccountBalance(savedTransaction);
        return savedTransaction;
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

    @Transactional
    public TransactionEntity update(final TransactionEntity transactionEntity) {
        TransactionEntity existingTransaction;
        try {
            existingTransaction = repository.findById(transactionEntity.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new NoEntityFoundException("Transaction with id " + transactionEntity.getId() + " not found");
        }

        TransactionEntity updatedTransaction = repository.update(transactionEntity);
        adjustAccountBalanceForUpdate(existingTransaction, updatedTransaction);

        return updatedTransaction;
    }


    @Transactional
    public TransactionEntity delete(final Integer id) {
        try {
            TransactionEntity transactionEntity = repository.findById(id);
            repository.delete(id);

            updateAccountBalanceOnDelete(transactionEntity);

            return transactionEntity;
        } catch (EmptyResultDataAccessException e) {
            throw new NoEntityFoundException("Transaction with id " + id + " not found");
        }
    }

    private void updateAccountBalance(TransactionEntity transactionEntity) {
        BankAccountEntity account = bankAccountService.findById(transactionEntity.getAccountId());
        int newBalance = account.getBalance();
        if (transactionEntity.getType() == TransactionType.INCOME) {
            newBalance += transactionEntity.getValue();
        } else {
            newBalance -= transactionEntity.getValue();
        }
        account.setBalance(newBalance);
        bankAccountService.update(account);
    }

    private void adjustAccountBalanceForUpdate(TransactionEntity oldTransaction, TransactionEntity newTransaction) {
        BankAccountEntity account = bankAccountService.findById(newTransaction.getAccountId());

        int oldValue = oldTransaction.getValue();
        TransactionType oldType = oldTransaction.getType();
        if (oldType == TransactionType.INCOME) {
            account.setBalance(account.getBalance() - oldValue);
        } else {
            account.setBalance(account.getBalance() + oldValue);
        }

        int newValue = newTransaction.getValue();
        TransactionType newType = newTransaction.getType();
        if (newType == TransactionType.INCOME) {
            account.setBalance(account.getBalance() + newValue);
        } else {
            account.setBalance(account.getBalance() - newValue);
        }

        bankAccountService.update(account);
    }

    private void updateAccountBalanceOnDelete(TransactionEntity deletedTransaction) {
        BankAccountEntity account = bankAccountService.findById(deletedTransaction.getAccountId());
        if (deletedTransaction.getType() == TransactionType.INCOME) {
            account.setBalance(account.getBalance() - deletedTransaction.getValue());
        } else {
            account.setBalance(account.getBalance() + deletedTransaction.getValue());
        }
        bankAccountService.update(account);
    }
}
