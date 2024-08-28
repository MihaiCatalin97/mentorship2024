package org.java.mentorship.budget.service;

import org.java.mentorship.budget.domain.BankAccountEntity;
import org.java.mentorship.budget.exception.UnauthorizedException;
import org.java.mentorship.budget.persistence.AccountRepository;
import org.java.mentorship.budget.exception.NoEntityFoundException;
import org.java.mentorship.budget.persistence.AccountRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

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
        try {
            return repository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoEntityFoundException("Account with id " + id + " not found");
        }
    }

    public BankAccountEntity update(final BankAccountEntity bankAccountEntity) {
        try {
            BankAccountEntity existingAccount = repository.findById(bankAccountEntity.getId());
            if (!Objects.equals(bankAccountEntity.getUserId(), existingAccount.getUserId())) {
                throw new UnauthorizedException("You can't edit the user id field of this entity");
            }
            return repository.update(bankAccountEntity);
        } catch (EmptyResultDataAccessException e) {
            throw new NoEntityFoundException("Account with id " + bankAccountEntity.getId() + " not found");
        }
    }

    public BankAccountEntity delete(final Integer id) {
        try {
            BankAccountEntity bankAccountEntity = repository.findById(id);
            repository.delete(id);
            return bankAccountEntity;
        } catch (EmptyResultDataAccessException e) {
            throw new NoEntityFoundException("Account with id " + id + " not found");
        }
    }
}
