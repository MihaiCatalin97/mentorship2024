package org.java.mentorship.budget.persistence.mapper;

import org.java.mentorship.budget.domain.AccountEntity;
import org.java.mentorship.contracts.budget.dto.AccountType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AccountRowMapper implements RowMapper<AccountEntity> {

    @Override
    public AccountEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        AccountEntity accountEntity = new AccountEntity();

        accountEntity.setId(rs.getInt("id"));
        accountEntity.setUserId(rs.getInt("user_id"));
        accountEntity.setName(rs.getString("name"));
        accountEntity.setType(AccountType.valueOf(rs.getString("type")));
        accountEntity.setBalance(rs.getInt("balance"));
        accountEntity.setCurrency(rs.getString("currency"));

        return accountEntity;
    }
}
