package org.java.mentorship.budget.validation;

import org.java.mentorship.budget.domain.BankAccountEntity;
import org.java.mentorship.budget.exception.FieldIsNullException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class AccountValidator {

    public void validate(final BankAccountEntity account) {
        if (isNull(account.getId())) {
            throw new FieldIsNullException("id");
        }
        if (isNull(account.getUserId())) {
            throw new FieldIsNullException("userId");
        }
        if (isNull(account.getName()) || account.getName().isEmpty()) {
            throw new FieldIsNullException("name");
        }
        if (isNull(account.getType())) {
            throw new FieldIsNullException("type");
        }
        if (isNull(account.getBalance())) {
            throw new FieldIsNullException("balance");
        }
        if (account.getBalance() < 0) {
            throw new IllegalArgumentException("Field 'balance' must be at least 0");
        }
        if (isNull(account.getCurrency()) || account.getCurrency().isEmpty()) {
            throw new FieldIsNullException("currency");
        }
    }
}
