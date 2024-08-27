package org.java.mentorship.budget.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.domain.BankAccountEntity;
import org.java.mentorship.budget.exception.UnauthorizedException;
import org.java.mentorship.budget.persistence.AccountRepository;
import org.java.mentorship.budget.exception.NoEntityFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    public BankAccountEntity save(final BankAccountEntity bankAccountEntity) {
        return repository.save(bankAccountEntity);
    }

    public List<BankAccountEntity> findAll() {
        return repository.findAll();
    }

    public BankAccountEntity findById(final Integer id) {
        BankAccountEntity bankAccountEntity = repository.findById(id);
        if (bankAccountEntity == null) {
            throw new NoEntityFoundException("Account with id " + id + " not found");
        }
        return bankAccountEntity;
    }

    public BankAccountEntity update(final BankAccountEntity bankAccountEntity) {
        BankAccountEntity existingAccount = repository.findById(bankAccountEntity.getId());
        if (!Objects.equals(bankAccountEntity.getUserId(), existingAccount.getUserId())) {
            throw new UnauthorizedException("You can't edit the user id field of this entity");
        }
        if (existingAccount == null) {
            throw new NoEntityFoundException("Account with id " + bankAccountEntity.getId() + " not found");
        }
        return repository.update(bankAccountEntity);
    }

    public BankAccountEntity delete(final Integer id) {
        BankAccountEntity bankAccountEntity = repository.findById(id);
        if (bankAccountEntity == null) {
            throw new NoEntityFoundException("Account with id " + id + " not found");
        }
        repository.delete(id);
        return bankAccountEntity;
    }
}
