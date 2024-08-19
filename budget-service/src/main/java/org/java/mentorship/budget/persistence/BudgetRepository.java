package org.java.mentorship.budget.persistence;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.domain.BudgetEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BudgetRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<BudgetEntity> rowMapper;

    public BudgetEntity save(final BudgetEntity budgetEntity) {
        jdbcTemplate.update(
                "INSERT INTO budgets (user_id, name, maximum_allowed, budget_interval, transaction_id, account_id) VALUES(?,?,?,?,?,?)",
                budgetEntity.getUserId(),
                budgetEntity.getName(),
                budgetEntity.getMaximumAllowed(),
                budgetEntity.getInterval().name(),
                budgetEntity.getTransactionId(),
                budgetEntity.getAccountId()
        );
        return budgetEntity;
    }

    public List<BudgetEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM budgets", rowMapper);
    }

    public BudgetEntity findById(final Integer id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM budgets WHERE id = ?",
                new Object[]{id},
                rowMapper
        );
    }

    public BudgetEntity update(final BudgetEntity budgetEntity) {
        jdbcTemplate.update(
                "UPDATE budgets SET name = ?, maximum_allowed = ?, budget_interval = ?, transaction_id = ?, account_id = ? WHERE id = ?",
                budgetEntity.getName(),
                budgetEntity.getMaximumAllowed(),
                budgetEntity.getInterval().name(),
                budgetEntity.getTransactionId(),
                budgetEntity.getAccountId(),
                budgetEntity.getId()
        );
        return budgetEntity;
    }

    public BudgetEntity delete(final Integer id) {
        BudgetEntity budgetEntity = findById(id);
        jdbcTemplate.update("DELETE FROM budgets WHERE id = ?", id);
        return budgetEntity;
    }
}
