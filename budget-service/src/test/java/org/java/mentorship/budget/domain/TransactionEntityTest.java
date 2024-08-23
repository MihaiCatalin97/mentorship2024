package org.java.mentorship.budget.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.java.mentorship.contracts.budget.dto.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TransactionEntityTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateShouldFailWhenIdIsNull() {
        TransactionEntity transaction = TransactionEntity.builder()
                .id(null)
                .userId(1)
                .type(TransactionType.INCOME)
                .value(100)
                .description("Test Transaction")
                .categoryId(1)
                .accountId(1)
                .timestamp(OffsetDateTime.now())
                .build();

        Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(transaction);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'id' must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenUserIdIsNull() {
        TransactionEntity transaction = TransactionEntity.builder()
                .id(1)
                .userId(null)
                .type(TransactionType.INCOME)
                .value(100)
                .description("Test Transaction")
                .categoryId(1)
                .accountId(1)
                .timestamp(OffsetDateTime.now())
                .build();

        Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(transaction);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'userId' must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenTypeIsNull() {
        TransactionEntity transaction = TransactionEntity.builder()
                .id(1)
                .userId(1)
                .type(null)
                .value(100)
                .description("Test Transaction")
                .categoryId(1)
                .accountId(1)
                .timestamp(OffsetDateTime.now())
                .build();

        Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(transaction);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'type' must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenValueIsNegative() {
        TransactionEntity transaction = TransactionEntity.builder()
                .id(1)
                .userId(1)
                .type(TransactionType.INCOME)
                .value(-10)
                .description("Test Transaction")
                .categoryId(1)
                .accountId(1)
                .timestamp(OffsetDateTime.now())
                .build();

        Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(transaction);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'value' must be at least 0", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenDescriptionIsEmpty() {
        TransactionEntity transaction = TransactionEntity.builder()
                .id(1)
                .userId(1)
                .type(TransactionType.INCOME)
                .value(100)
                .description("")
                .categoryId(1)
                .accountId(1)
                .timestamp(OffsetDateTime.now())
                .build();

        Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(transaction);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'description' must not be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenDescriptionIsTooLong() {
        String longDescription = "a".repeat(256);

        TransactionEntity transaction = TransactionEntity.builder()
                .id(1)
                .userId(1)
                .type(TransactionType.INCOME)
                .value(100)
                .description(longDescription)
                .categoryId(1)
                .accountId(1)
                .timestamp(OffsetDateTime.now())
                .build();

        Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(transaction);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'description' must not exceed 255 characters", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenCategoryIdIsNull() {
        TransactionEntity transaction = TransactionEntity.builder()
                .id(1)
                .userId(1)
                .type(TransactionType.INCOME)
                .value(100)
                .description("Test Transaction")
                .categoryId(null)
                .accountId(1)
                .timestamp(OffsetDateTime.now())
                .build();

        Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(transaction);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'categoryId' must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenCategoryIdIsNegative() {
        TransactionEntity transaction = TransactionEntity.builder()
                .id(1)
                .userId(1)
                .type(TransactionType.INCOME)
                .value(100)
                .description("Test Transaction")
                .categoryId(-1)
                .accountId(1)
                .timestamp(OffsetDateTime.now())
                .build();

        Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(transaction);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'categoryId' must be greater than 0", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenAccountIdIsNull() {
        TransactionEntity transaction = TransactionEntity.builder()
                .id(1)
                .userId(1)
                .type(TransactionType.INCOME)
                .value(100)
                .description("Test Transaction")
                .categoryId(1)
                .accountId(null)
                .timestamp(OffsetDateTime.now())
                .build();

        Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(transaction);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'accountId' must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenAccountIdIsNegative() {
        TransactionEntity transaction = TransactionEntity.builder()
                .id(1)
                .userId(1)
                .type(TransactionType.INCOME)
                .value(100)
                .description("Test Transaction")
                .categoryId(1)
                .accountId(-1)
                .timestamp(OffsetDateTime.now())
                .build();

        Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(transaction);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'accountId' must be greater than 0", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenTimestampIsNull() {
        TransactionEntity transaction = TransactionEntity.builder()
                .id(1)
                .userId(1)
                .type(TransactionType.INCOME)
                .value(100)
                .description("Test Transaction")
                .categoryId(1)
                .accountId(1)
                .timestamp(null)
                .build();

        Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(transaction);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'timestamp' must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldPassWhenAllFieldsAreValid() {
        TransactionEntity transaction = TransactionEntity.builder()
                .id(1)
                .userId(1)
                .type(TransactionType.INCOME)
                .value(100)
                .description("Test Transaction")
                .categoryId(1)
                .accountId(1)
                .timestamp(OffsetDateTime.now())
                .build();

        Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(transaction);

        assertTrue(violations.isEmpty(), "Expected no validation errors");
    }
}
