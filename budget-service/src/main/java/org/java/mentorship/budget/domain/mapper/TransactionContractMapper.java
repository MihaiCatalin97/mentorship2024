package org.java.mentorship.budget.domain.mapper;

import org.java.mentorship.budget.domain.TransactionEntity;
import org.java.mentorship.contracts.budget.dto.Transaction;

public class TransactionContractMapper {

    public static Transaction entityToContract(TransactionEntity transactionEntity) {
        Transaction transactionContract = new Transaction();

        transactionContract.setId(transactionEntity.getId());
        transactionContract.setUserId(transactionEntity.getUserId());
        transactionContract.setType(transactionEntity.getType());
        transactionContract.setValue(transactionEntity.getValue());
        transactionContract.setDescription(transactionEntity.getDescription());
        transactionContract.setAccountId(transactionEntity.getAccountId());
        transactionContract.setTimestamp(transactionEntity.getTimestamp());

        return transactionContract;
    }

    public static TransactionEntity contractToEntity(Transaction transactionContract) {
        TransactionEntity transactionEntity = new TransactionEntity();

        transactionEntity.setId(transactionContract.getId());
        transactionEntity.setUserId(transactionContract.getUserId());
        transactionEntity.setType(transactionContract.getType());
        transactionEntity.setValue(transactionContract.getValue());
        transactionEntity.setDescription(transactionContract.getDescription());
        transactionEntity.setAccountId(transactionContract.getAccountId());
        transactionEntity.setTimestamp(transactionContract.getTimestamp());

        return transactionEntity;
    }
}
