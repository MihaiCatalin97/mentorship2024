package org.java.mentorship.budget.persistence;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.domain.TransactionEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<TransactionEntity> rowMapper;

    public TransactionEntity save(final TransactionEntity transactionEntity) {
        jdbcTemplate.update(
                "INSERT INTO transactions (user_id, type, value, description, account_id, timestamp) VALUES(?,?,?,?,?,?)",
                transactionEntity.getUserId(),
                transactionEntity.getType().name(),
                transactionEntity.getValue(),
                transactionEntity.getDescription(),
                transactionEntity.getAccountId(),
                transactionEntity.getTimestamp()
        );
        return transactionEntity;
    }

    public List<TransactionEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM transactions", rowMapper);
    }

    public TransactionEntity findById(final Integer id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM transactions WHERE id = ?",
                new Object[]{id},
                rowMapper
        );
    }

    public TransactionEntity update(final TransactionEntity transactionEntity) {
        jdbcTemplate.update(
                "UPDATE transactions SET user_id = ?, type = ?, value = ?, description = ?, account_id = ?, timestamp = ? WHERE id = ?",
                transactionEntity.getUserId(),
                transactionEntity.getType().name(),
                transactionEntity.getValue(),
                transactionEntity.getDescription(),
                transactionEntity.getAccountId(),
                transactionEntity.getTimestamp(),
                transactionEntity.getId()
        );
        return transactionEntity;
    }

    public TransactionEntity delete(final Integer id) {
        TransactionEntity transactionEntity = findById(id);
        if (transactionEntity != null) {
            jdbcTemplate.update("DELETE FROM transactions WHERE id = ?", id);
        }
        return transactionEntity;
    }
}
