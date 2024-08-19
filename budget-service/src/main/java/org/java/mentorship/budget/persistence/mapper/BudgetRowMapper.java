package org.java.mentorship.budget.persistence.mapper;

import org.java.mentorship.budget.domain.BudgetEntity;
import org.java.mentorship.contracts.budget.dto.BudgetInterval;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BudgetRowMapper implements RowMapper<BudgetEntity> {

    @Override
    public BudgetEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        BudgetEntity budgetEntity = new BudgetEntity();

        budgetEntity.setId(rs.getInt("id"));
        budgetEntity.setUserId(rs.getInt("user_id"));
        budgetEntity.setName(rs.getString("name"));
        budgetEntity.setMaximumAllowed(rs.getInt("maximum_allowed"));
        budgetEntity.setInterval(BudgetInterval.valueOf(rs.getString("budget_interval")));
        budgetEntity.setTransactionId(rs.getInt("transaction_id"));
        budgetEntity.setAccountId(rs.getInt("account_id"));

        return budgetEntity;
    }
}
