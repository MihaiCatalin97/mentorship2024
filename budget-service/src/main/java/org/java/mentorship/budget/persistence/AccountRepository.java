package org.java.mentorship.budget.persistence;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.domain.AccountEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<AccountEntity> rowMapper;

    public AccountEntity save(final AccountEntity accountEntity) {
        jdbcTemplate.update(
                "INSERT INTO accounts (user_id, name, type, balance, currency) VALUES(?,?,?,?,?)",
                accountEntity.getUserId(),
                accountEntity.getName(),
                accountEntity.getType().name(),
                accountEntity.getBalance(),
                accountEntity.getCurrency()
        );
        return accountEntity;
    }

    public List<AccountEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM accounts", rowMapper);
    }

    public AccountEntity findById(final Integer id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM accounts WHERE id = ?",
                new Object[]{id},
                rowMapper
        );
    }

    public AccountEntity update(final AccountEntity accountEntity) {
        jdbcTemplate.update(
                "UPDATE accounts SET user_id = ?, name = ?, type = ?, balance = ?, currency = ? WHERE id = ?",
                accountEntity.getUserId(),
                accountEntity.getName(),
                accountEntity.getType().name(),
                accountEntity.getBalance(),
                accountEntity.getCurrency(),
                accountEntity.getId()
        );
        return accountEntity;
    }

    public AccountEntity delete(final Integer id) {
        AccountEntity accountEntity = findById(id);
        if (accountEntity != null) {
            jdbcTemplate.update("DELETE FROM accounts WHERE id = ?", id);
        }
        return accountEntity;
    }
}
