package org.java.mentorship.budget.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.java.mentorship.contracts.budget.dto.AccountType;
import org.java.mentorship.contracts.budget.dto.CurrencyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountEntityTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateShouldFailWhenIdIsNull() {
        BankAccountEntity account = BankAccountEntity.builder()
                .id(null)
                .userId(1)
                .name("Test Account")
                .type(AccountType.CHECKING)
                .balance(100)
                .currency(CurrencyType.USD)
                .build();

        Set<ConstraintViolation<BankAccountEntity>> violations = validator.validate(account);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'id' must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenUserIdIsNull() {
        BankAccountEntity account = BankAccountEntity.builder()
                .id(1)
                .userId(null)
                .name("Test Account")
                .type(AccountType.CHECKING)
                .balance(100)
                .currency(CurrencyType.USD)
                .build();

        Set<ConstraintViolation<BankAccountEntity>> violations = validator.validate(account);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'userId' must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenNameIsEmpty() {
        BankAccountEntity account = BankAccountEntity.builder()
                .id(1)
                .userId(1)
                .name("")
                .type(AccountType.CHECKING)
                .balance(100)
                .currency(CurrencyType.USD)
                .build();

        Set<ConstraintViolation<BankAccountEntity>> violations = validator.validate(account);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'name' must not be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenBalanceIsNegative() {
        BankAccountEntity account = BankAccountEntity.builder()
                .id(1)
                .userId(1)
                .name("Test Account")
                .type(AccountType.CHECKING)
                .balance(-1)
                .currency(CurrencyType.USD)
                .build();

        Set<ConstraintViolation<BankAccountEntity>> violations = validator.validate(account);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'balance' must be at least 0", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldPassWhenAllFieldsAreValid() {
        BankAccountEntity account = BankAccountEntity.builder()
                .id(1)
                .userId(1)
                .name("Test Account")
                .type(AccountType.CHECKING)
                .balance(100)
                .currency(CurrencyType.USD)
                .build();

        Set<ConstraintViolation<BankAccountEntity>> violations = validator.validate(account);

        assertTrue(violations.isEmpty(), "Expected no validation errors");
    }
}
