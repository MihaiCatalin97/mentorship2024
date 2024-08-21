package org.java.mentorship.budget.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoryEntityTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateShouldFailWhenIdIsNull() {
        CategoryEntity category = CategoryEntity.builder()
                .id(null)
                .name("Test Category")
                .userId(1)
                .build();

        Set<ConstraintViolation<CategoryEntity>> violations = validator.validate(category);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'id' must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenIdIsNegative() {
        CategoryEntity category = CategoryEntity.builder()
                .id(-1)
                .name("Test Category")
                .userId(1)
                .build();

        Set<ConstraintViolation<CategoryEntity>> violations = validator.validate(category);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'id' must be greater than 0", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenNameIsEmpty() {
        CategoryEntity category = CategoryEntity.builder()
                .id(1)
                .name("")
                .userId(1)
                .build();

        Set<ConstraintViolation<CategoryEntity>> violations = validator.validate(category);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'name' must not be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenNameIsTooLong() {
        String longName = "a".repeat(101); // 101 characters

        CategoryEntity category = CategoryEntity.builder()
                .id(1)
                .name(longName)
                .userId(1)
                .build();

        Set<ConstraintViolation<CategoryEntity>> violations = validator.validate(category);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'name' must not exceed 100 characters", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenUserIdIsNull() {
        CategoryEntity category = CategoryEntity.builder()
                .id(1)
                .name("Test Category")
                .userId(null)
                .build();

        Set<ConstraintViolation<CategoryEntity>> violations = validator.validate(category);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'userId' must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldFailWhenUserIdIsNegative() {
        CategoryEntity category = CategoryEntity.builder()
                .id(1)
                .name("Test Category")
                .userId(-1)
                .build();

        Set<ConstraintViolation<CategoryEntity>> violations = validator.validate(category);

        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertEquals(1, violations.size(), "Expected one validation error");
        assertEquals("Field 'userId' must be greater than 0", violations.iterator().next().getMessage());
    }

    @Test
    void validateShouldPassWhenAllFieldsAreValid() {
        CategoryEntity category = CategoryEntity.builder()
                .id(1)
                .name("Valid Category")
                .userId(1)
                .build();

        Set<ConstraintViolation<CategoryEntity>> violations = validator.validate(category);

        assertTrue(violations.isEmpty(), "Expected no validation errors");
    }
}
