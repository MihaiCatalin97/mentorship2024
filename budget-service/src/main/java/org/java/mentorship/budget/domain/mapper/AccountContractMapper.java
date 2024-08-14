package org.java.mentorship.budget.domain.mapper;

import org.java.mentorship.budget.domain.AccountEntity;
import org.java.mentorship.contracts.budget.dto.Account;

public class AccountContractMapper {

    public static Account entityToContract(AccountEntity accountEntity) {
        Account accountContract = new Account();

        accountContract.setId(accountEntity.getId());
        accountContract.setUserId(accountEntity.getUserId());
        accountContract.setName(accountEntity.getName());
        accountContract.setType(accountEntity.getType());
        accountContract.setBalance(accountEntity.getBalance());
        accountContract.setCurrency(accountEntity.getCurrency());

        return accountContract;
    }

    public static AccountEntity contractToEntity(Account accountContract) {
        AccountEntity accountEntity = new AccountEntity();

        accountEntity.setId(accountContract.getId());
        accountEntity.setUserId(accountContract.getUserId());
        accountEntity.setName(accountContract.getName());
        accountEntity.setType(accountContract.getType());
        accountEntity.setBalance(accountContract.getBalance());
        accountEntity.setCurrency(accountContract.getCurrency());

        return accountEntity;
    }
}
