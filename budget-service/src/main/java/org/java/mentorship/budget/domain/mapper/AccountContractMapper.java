package org.java.mentorship.budget.domain.mapper;

import org.java.mentorship.budget.domain.BankAccountEntity;
import org.java.mentorship.contracts.budget.dto.Account;

public class AccountContractMapper {

    public static Account entityToContract(BankAccountEntity bankAccountEntity) {
        Account accountContract = new Account();

        accountContract.setId(bankAccountEntity.getId());
        accountContract.setUserId(bankAccountEntity.getUserId());
        accountContract.setName(bankAccountEntity.getName());
        accountContract.setType(bankAccountEntity.getType());
        accountContract.setBalance(bankAccountEntity.getBalance());
        accountContract.setCurrency(bankAccountEntity.getCurrency());

        return accountContract;
    }

    public static BankAccountEntity contractToEntity(Account accountContract) {
        BankAccountEntity bankAccountEntity = new BankAccountEntity();

        bankAccountEntity.setId(accountContract.getId());
        bankAccountEntity.setUserId(accountContract.getUserId());
        bankAccountEntity.setName(accountContract.getName());
        bankAccountEntity.setType(accountContract.getType());
        bankAccountEntity.setBalance(accountContract.getBalance());
        bankAccountEntity.setCurrency(accountContract.getCurrency());

        return bankAccountEntity;
    }
}
