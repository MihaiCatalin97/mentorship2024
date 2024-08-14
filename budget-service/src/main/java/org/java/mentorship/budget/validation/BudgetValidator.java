package org.java.mentorship.budget.validation;

import org.java.mentorship.budget.domain.BudgetEntity;
import org.java.mentorship.budget.exception.FieldIsNullException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class BudgetValidator {

    public void validate(final BudgetEntity budget) {
        if (isNull(budget.getId())) {
            throw new FieldIsNullException("id");
        }
        if (budget.getId() < 1) {
            throw new IllegalArgumentException("Field 'id' must be greater than 0");
        }
        if (isNull(budget.getUserId())) {
            throw new FieldIsNullException("userId");
        }
        if (budget.getUserId() < 1) {
            throw new IllegalArgumentException("Field 'userId' must be greater than 0");
        }
        if (isNull(budget.getName()) || budget.getName().isEmpty()) {
            throw new FieldIsNullException("name");
        }
        if (budget.getName().length() > 100) {
            throw new IllegalArgumentException("Field 'name' must not exceed 100 characters");
        }
        if (isNull(budget.getMaximumAllowed())) {
            throw new FieldIsNullException("maximumAllowed");
        }
        if (budget.getMaximumAllowed() < 0) {
            throw new IllegalArgumentException("Field 'maximumAllowed' must be at least 0");
        }
        if (isNull(budget.getInterval())) {
            throw new FieldIsNullException("interval");
        }
        if (isNull(budget.getCurrentUsage())) {
            throw new FieldIsNullException("currentUsage");
        }
        if (budget.getCurrentUsage() < 0) {
            throw new IllegalArgumentException("Field 'currentUsage' must be at least 0");
        }
        if (isNull(budget.getTransactionId())) {
            throw new FieldIsNullException("transactionId");
        }
        if (budget.getTransactionId() < 1) {
            throw new IllegalArgumentException("Field 'transactionId' must be greater than 0");
        }
        if (isNull(budget.getAccountId())) {
            throw new FieldIsNullException("accountId");
        }
        if (budget.getAccountId() < 1) {
            throw new IllegalArgumentException("Field 'accountId' must be greater than 0");
        }
    }
}
