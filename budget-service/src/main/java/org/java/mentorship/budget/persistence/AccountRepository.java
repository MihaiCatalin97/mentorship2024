package org.java.mentorship.budget.persistence;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.domain.BankAccountEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<BankAccountEntity> rowMapper;

    public BankAccountEntity save(final BankAccountEntity bankAccountEntity) {
        jdbcTemplate.update(
                "INSERT INTO accounts (user_id, name, type, balance, currency) VALUES(?,?,?,?,?)",
                bankAccountEntity.getUserId(),
                bankAccountEntity.getName(),
                bankAccountEntity.getType().name(),
                bankAccountEntity.getBalance(),
                bankAccountEntity.getCurrency().name()
        );
        return bankAccountEntity;
    }

    public List<BankAccountEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM accounts", rowMapper);
    }

    public BankAccountEntity findById(final Integer id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM accounts WHERE id = ?",
                new Object[]{id},
                rowMapper
        );
    }

    public BankAccountEntity update(final BankAccountEntity accountEntity) {
        jdbcTemplate.update(
                "UPDATE accounts SET name = ?, type = ?, balance = ?, currency = ? WHERE id = ?",
                accountEntity.getName(),
                accountEntity.getType().name(),
                accountEntity.getBalance(),
                accountEntity.getCurrency().name(),
                accountEntity.getId()
        );
        return accountEntity;
    }


    public BankAccountEntity delete(final Integer id) {
        BankAccountEntity bankAccountEntity = findById(id);
        jdbcTemplate.update("DELETE FROM accounts WHERE id = ?", id);
        return bankAccountEntity;
    }
}
