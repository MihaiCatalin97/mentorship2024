package org.java.mentorship.budget.validation;

import org.java.mentorship.budget.domain.TransactionEntity;
import org.java.mentorship.budget.exception.FieldIsNullException;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

import static java.util.Objects.isNull;

@Component
public class TransactionValidator {

    public void validate(final TransactionEntity transaction) {
        if (isNull(transaction.getId())) {
            throw new FieldIsNullException("id");
        }
        if (transaction.getId() < 1) {
            throw new IllegalArgumentException("Field 'id' must be greater than 0");
        }
        if (isNull(transaction.getUserId())) {
            throw new FieldIsNullException("userId");
        }
        if (transaction.getUserId() < 1) {
            throw new IllegalArgumentException("Field 'userId' must be greater than 0");
        }
        if (isNull(transaction.getType())) {
            throw new FieldIsNullException("type");
        }
        if (isNull(transaction.getValue())) {
            throw new FieldIsNullException("value");
        }
        if (transaction.getValue() < 0) {
            throw new IllegalArgumentException("Field 'value' must be at least 0");
        }
        if (isNull(transaction.getDescription()) || transaction.getDescription().isEmpty()) {
            throw new FieldIsNullException("description");
        }
        if (transaction.getDescription().length() > 255) {
            throw new IllegalArgumentException("Field 'description' must not exceed 255 characters");
        }
        if (isNull(transaction.getAccountId())) {
            throw new FieldIsNullException("accountId");
        }
        if (transaction.getAccountId() < 1) {
            throw new IllegalArgumentException("Field 'accountId' must be greater than 0");
        }
        if (isNull(transaction.getTimestamp())) {
            throw new FieldIsNullException("timestamp");
        }
        if (transaction.getTimestamp().isAfter(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Field 'timestamp' cannot be in the future");
        }
    }
}
