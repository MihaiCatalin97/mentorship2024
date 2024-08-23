package org.java.mentorship.budget.persistence.mapper;

import org.java.mentorship.budget.domain.BankAccountEntity;
import org.java.mentorship.contracts.budget.dto.AccountType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AccountRowMapper implements RowMapper<BankAccountEntity> {

    @Override
    public BankAccountEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        BankAccountEntity bankAccountEntity = new BankAccountEntity();

        bankAccountEntity.setId(rs.getInt("id"));
        bankAccountEntity.setUserId(rs.getInt("user_id"));
        bankAccountEntity.setName(rs.getString("name"));
        bankAccountEntity.setType(AccountType.valueOf(rs.getString("type")));
        bankAccountEntity.setBalance(rs.getInt("balance"));
        bankAccountEntity.setCurrency(rs.getString("currency"));

        return bankAccountEntity;
    }
}
