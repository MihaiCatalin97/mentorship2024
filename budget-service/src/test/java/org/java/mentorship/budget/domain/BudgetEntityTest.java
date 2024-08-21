package org.java.mentorship.budget.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.java.mentorship.contracts.budget.dto.BudgetInterval;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BudgetEntityTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateShouldFailWhenIdIsNull() {
        BudgetEntity budget = BudgetEntity.builder()
                .id(null)
                .userId(1)
                .name("Test Budget")
                .maximumAllowed(1000)
                .interval(BudgetInterval.MONTHLY)
                .categoryId(1)
                .accountId(1)
                .build();

        Set<ConstraintViolation<BudgetEntity>> violations = validator.validate(budget);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'id' must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenUserIdIsNull() {
        BudgetEntity budget = BudgetEntity.builder()
                .id(1)
                .userId(null)
                .name("Test Budget")
                .maximumAllowed(1000)
                .interval(BudgetInterval.MONTHLY)
                .categoryId(1)
                .accountId(1)
                .build();

        Set<ConstraintViolation<BudgetEntity>> violations = validator.validate(budget);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'userId' must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenNameIsEmpty() {
        BudgetEntity budget = BudgetEntity.builder()
                .id(1)
                .userId(1)
                .name("")
                .maximumAllowed(1000)
                .interval(BudgetInterval.MONTHLY)
                .categoryId(1)
                .accountId(1)
                .build();

        Set<ConstraintViolation<BudgetEntity>> violations = validator.validate(budget);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'name' must not be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenMaximumAllowedIsNegative() {
        BudgetEntity budget = BudgetEntity.builder()
                .id(1)
                .userId(1)
                .name("Test Budget")
                .maximumAllowed(-100)
                .interval(BudgetInterval.MONTHLY)
                .categoryId(1)
                .accountId(1)
                .build();

        Set<ConstraintViolation<BudgetEntity>> violations = validator.validate(budget);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'maximumAllowed' must be at least 0", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenIntervalIsNull() {
        BudgetEntity budget = BudgetEntity.builder()
                .id(1)
                .userId(1)
                .name("Test Budget")
                .maximumAllowed(1000)
                .interval(null)
                .categoryId(1)
                .accountId(1)
                .build();

        Set<ConstraintViolation<BudgetEntity>> violations = validator.validate(budget);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'interval' must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenCategoryIdIsNull() {
        BudgetEntity budget = BudgetEntity.builder()
                .id(1)
                .userId(1)
                .name("Test Budget")
                .maximumAllowed(1000)
                .interval(BudgetInterval.MONTHLY)
                .categoryId(null)
                .accountId(1)
                .build();

        Set<ConstraintViolation<BudgetEntity>> violations = validator.validate(budget);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'categoryId' must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenCategoryIdIsNegative() {
        BudgetEntity budget = BudgetEntity.builder()
                .id(1)
                .userId(1)
                .name("Test Budget")
                .maximumAllowed(1000)
                .interval(BudgetInterval.MONTHLY)
                .categoryId(-1)
                .accountId(1)
                .build();

        Set<ConstraintViolation<BudgetEntity>> violations = validator.validate(budget);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'categoryId' must be greater than 0", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenAccountIdIsNull() {
        BudgetEntity budget = BudgetEntity.builder()
                .id(1)
                .userId(1)
                .name("Test Budget")
                .maximumAllowed(1000)
                .interval(BudgetInterval.MONTHLY)
                .categoryId(1)
                .accountId(null)
                .build();

        Set<ConstraintViolation<BudgetEntity>> violations = validator.validate(budget);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'accountId' must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenAccountIdIsNegative() {
        BudgetEntity budget = BudgetEntity.builder()
                .id(1)
                .userId(1)
                .name("Test Budget")
                .maximumAllowed(1000)
                .interval(BudgetInterval.MONTHLY)
                .categoryId(1)
                .accountId(-1)
                .build();

        Set<ConstraintViolation<BudgetEntity>> violations = validator.validate(budget);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'accountId' must be greater than 0", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldPassWhenAllFieldsAreValid() {
        BudgetEntity budget = BudgetEntity.builder()
                .id(1)
                .userId(1)
                .name("Test Budget")
                .maximumAllowed(1000)
                .interval(BudgetInterval.MONTHLY)
                .categoryId(1)
                .accountId(1)
                .build();

        Set<ConstraintViolation<BudgetEntity>> violations = validator.validate(budget);

        assertTrue(violations.isEmpty(), "Expected no validation errors");
    }
}
