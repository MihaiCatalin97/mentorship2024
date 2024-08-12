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
        if (isNull(budget.getTransactionTypes()) || budget.getTransactionTypes().isEmpty()) {
            throw new FieldIsNullException("transactionTypes");
        }
        if (isNull(budget.getCurrentUsage())) {
            throw new FieldIsNullException("currentUsage");
        }
    }
}
