package org.java.mentorship.budget.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.domain.AccountEntity;
import org.java.mentorship.budget.persistence.AccountRepository;
import org.java.mentorship.budget.exception.NoEntityFoundException;
import org.java.mentorship.budget.validation.AccountValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;
    private final AccountValidator validator;

    public AccountEntity save(final AccountEntity accountEntity) {
        validator.validate(accountEntity);
        return repository.save(accountEntity);
    }

    public List<AccountEntity> findAll() {
        return repository.findAll();
    }

    public AccountEntity findById(final Integer id) {
        AccountEntity accountEntity = repository.findById(id);
        if (accountEntity == null) {
            throw new NoEntityFoundException("Account with id " + id + " not found");
        }
        return accountEntity;
    }

    public AccountEntity update(final AccountEntity accountEntity) {
        validator.validate(accountEntity);
        AccountEntity existingAccount = repository.findById(accountEntity.getId());
        if (existingAccount == null) {
            throw new NoEntityFoundException("Account with id " + accountEntity.getId() + " not found");
        }
        return repository.update(accountEntity);
    }

    public AccountEntity delete(final Integer id) {
        AccountEntity accountEntity = repository.findById(id);
        if (accountEntity == null) {
            throw new NoEntityFoundException("Account with id " + id + " not found");
        }
        repository.delete(id);
        return accountEntity;
    }
}
