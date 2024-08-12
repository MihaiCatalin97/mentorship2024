package org.java.mentorship.budget.validation;

import org.java.mentorship.contracts.budget.dto.Budget;
import org.java.mentorship.budget.exception.FieldIsNullException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class BudgetValidator {

    public void validate(final Budget budget) {
        if (isNull(budget.getId())) {
            throw new FieldIsNullException("id");
        }
        if (isNull(budget.getUserId())) {
            throw new FieldIsNullException("userId");
        }
        if (isNull(budget.getName())) {
            throw new FieldIsNullException("name");
        }
        if (isNull(budget.getMaximumAllowed())) {
            throw new FieldIsNullException("maximumAllowed");
        }
        if (isNull(budget.getInterval())) {
            throw new FieldIsNullException("interval");
        }
        if (isNull(budget.getCurrentUsage())) {
            throw new FieldIsNullException("currentUsage");
        }

        if (isNull(budget.getAccounts()) || budget.getAccounts().isEmpty()) {
            throw new FieldIsNullException("accounts");
        }

        if (isNull(budget.getTransactions()) || budget.getTransactions().isEmpty()) {
            throw new FieldIsNullException("transactions");
        }

        for (var account : budget.getAccounts()) {
            if (isNull(account.getId())) {
                throw new FieldIsNullException("account.id");
            }
            if (isNull(account.getUserId())) {
                throw new FieldIsNullException("account.userId");
            }
            if (isNull(account.getName())) {
                throw new FieldIsNullException("account.name");
            }
            if (isNull(account.getType())) {
                throw new FieldIsNullException("account.type");
            }
            if (isNull(account.getBalance())) {
                throw new FieldIsNullException("account.balance");
            }
            if (isNull(account.getCurrency())) {
                throw new FieldIsNullException("account.currency");
            }
        }

        for (var transaction : budget.getTransactions()) {
            if (isNull(transaction.getId())) {
                throw new FieldIsNullException("transaction.id");
            }
            if (isNull(transaction.getUserId())) {
                throw new FieldIsNullException("transaction.userId");
            }
            if (isNull(transaction.getType())) {
                throw new FieldIsNullException("transaction.type");
            }
            if (isNull(transaction.getValue())) {
                throw new FieldIsNullException("transaction.value");
            }
            if (isNull(transaction.getSourceAccountId())) {
                throw new FieldIsNullException("transaction.sourceAccountId");
            }
            if (isNull(transaction.getDescription())) {
                throw new FieldIsNullException("transaction.description");
            }
            if (isNull(transaction.getTimestamp())) {
                throw new FieldIsNullException("transaction.timestamp");
            }
        }
    }
}
