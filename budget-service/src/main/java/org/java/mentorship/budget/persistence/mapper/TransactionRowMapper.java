package org.java.mentorship.budget.persistence.mapper;

import org.java.mentorship.budget.domain.TransactionEntity;
import org.java.mentorship.contracts.budget.dto.TransactionType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

@Component
public class TransactionRowMapper implements RowMapper<TransactionEntity> {

    @Override
    public TransactionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransactionEntity transactionEntity = new TransactionEntity();

        transactionEntity.setId(rs.getInt("id"));
        transactionEntity.setUserId(rs.getInt("user_id"));
        transactionEntity.setType(TransactionType.valueOf(rs.getString("type")));
        transactionEntity.setValue(rs.getInt("transaction_value"));
        transactionEntity.setDescription(rs.getString("description"));
        transactionEntity.setCategoryId(rs.getInt("category_id"));
        transactionEntity.setAccountId(rs.getInt("account_id"));

        // Convert SQL timestamp to OffsetDateTime
        transactionEntity.setTimestamp(rs.getObject("timestamp", OffsetDateTime.class));

        return transactionEntity;
    }
}
