package org.java.mentorship.budget.domain.mapper;

import org.java.mentorship.budget.domain.BudgetEntity;
import org.java.mentorship.contracts.budget.dto.Budget;

public class BudgetContractMapper {

    public static Budget entityToContract(BudgetEntity budgetEntity) {
        Budget budgetContract = new Budget();

        budgetContract.setId(budgetEntity.getId());
        budgetContract.setUserId(budgetEntity.getUserId());
        budgetContract.setName(budgetEntity.getName());
        budgetContract.setMaximumAllowed(budgetEntity.getMaximumAllowed());
        budgetContract.setInterval(budgetEntity.getInterval());
        budgetContract.setCurrentUsage(budgetEntity.getCurrentUsage());
        budgetContract.setTransactionId(budgetEntity.getTransactionId());
        budgetContract.setAccountId(budgetEntity.getAccountId());

        return budgetContract;
    }

    public static BudgetEntity contractToEntity(Budget budgetContract) {
        BudgetEntity budgetEntity = new BudgetEntity();

        budgetEntity.setId(budgetContract.getId());
        budgetEntity.setUserId(budgetContract.getUserId());
        budgetEntity.setName(budgetContract.getName());
        budgetEntity.setMaximumAllowed(budgetContract.getMaximumAllowed());
        budgetEntity.setInterval(budgetContract.getInterval());
        budgetEntity.setCurrentUsage(budgetContract.getCurrentUsage());
        budgetEntity.setTransactionId(budgetContract.getTransactionId());
        budgetEntity.setAccountId(budgetContract.getAccountId());

        return budgetEntity;
    }
}
