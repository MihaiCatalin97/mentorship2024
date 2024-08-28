package org.java.mentorship.budget.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.domain.BudgetEntity;
import org.java.mentorship.budget.exception.NoEntityFoundException;
import org.java.mentorship.budget.persistence.BudgetRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository repository;

    @Transactional
    public BudgetEntity save(final BudgetEntity budgetEntity) {
        return repository.save(budgetEntity);
    }

    public List<BudgetEntity> findAll(Integer userId) {
        if (userId != null) {
            return repository.findByUserId(userId);
        }
        return repository.findAll();
    }

    public BudgetEntity findById(final Integer id) {
        try {
            return repository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoEntityFoundException("Budget with id " + id + " not found");
        }
    }

    @Transactional
    public BudgetEntity update(final BudgetEntity budgetEntity) {
        try {
            BudgetEntity existingBudget = repository.findById(budgetEntity.getId());
            return repository.update(budgetEntity);
        } catch (EmptyResultDataAccessException e) {
            throw new NoEntityFoundException("Budget with id " + budgetEntity.getId() + " not found");
        }
    }

    @Transactional
    public BudgetEntity delete(final Integer id) {
        try {
            BudgetEntity budgetEntity = repository.findById(id);
            repository.delete(id);
            return budgetEntity;
        } catch (EmptyResultDataAccessException e) {
            throw new NoEntityFoundException("Budget with id " + id + " not found");
        }
    }
}
