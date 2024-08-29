package org.java.mentorship.budget.persistence;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.domain.TransactionEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<TransactionEntity> rowMapper;

    public TransactionEntity save(final TransactionEntity transactionEntity) {
        jdbcTemplate.update(
                "INSERT INTO transactions (user_id, type, transaction_value, description, category_id, account_id, timestamp) VALUES(?,?,?,?,?,?,?)",
                transactionEntity.getUserId(),
                transactionEntity.getType().name(),
                transactionEntity.getValue(),
                transactionEntity.getDescription(),
                transactionEntity.getCategoryId(),
                transactionEntity.getAccountId(),
                transactionEntity.getTimestamp()
        );
        return transactionEntity;
    }

    public List<TransactionEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM transactions ORDER BY id", rowMapper);
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
                "UPDATE transactions SET type = ?, transaction_value = ?, description = ?, category_id = ?, timestamp = ? WHERE id = ?",
                transactionEntity.getType().name(),
                transactionEntity.getValue(),
                transactionEntity.getDescription(),
                transactionEntity.getCategoryId(),
                transactionEntity.getTimestamp(),
                transactionEntity.getId()
        );
        return transactionEntity;
    }


    public TransactionEntity delete(final Integer id) {
        TransactionEntity transactionEntity = findById(id);
        jdbcTemplate.update("DELETE FROM transactions WHERE id = ?", id);
        return transactionEntity;
    }

    public List<TransactionEntity> findTransactionsFromLast7Days() {
        OffsetDateTime sevenDaysAgo = OffsetDateTime.now(ZoneOffset.UTC).minusDays(7);
        return jdbcTemplate.query(
                "SELECT * FROM transactions WHERE timestamp >= ? ORDER BY id",
                new Object[]{sevenDaysAgo},
                rowMapper
        );
    }

}
