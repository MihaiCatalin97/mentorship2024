package org.java.mentorship.budget.validation;

import org.java.mentorship.budget.domain.BudgetEntity;
import org.java.mentorship.budget.exception.FieldIsNullException;
import org.java.mentorship.contracts.budget.dto.BudgetInterval;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BudgetValidatorTest {

    private BudgetValidator budgetValidator;

    @BeforeEach
    void initialize() {
        budgetValidator = new BudgetValidator();
    }

    @Test
    void validateShouldThrowExceptionWhenIdIsNull() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setId(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> budgetValidator.validate(budget),
                "Expected exception to be thrown");

        assertEquals("Field 'id' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenIdIsLessThanOne() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setId(0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> budgetValidator.validate(budget),
                "Expected exception to be thrown");

        assertEquals("Field 'id' must be greater than 0", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenUserIdIsNull() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setId(1);
        budget.setUserId(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> budgetValidator.validate(budget),
                "Expected exception to be thrown");

        assertEquals("Field 'userId' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenUserIdIsLessThanOne() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setId(1);
        budget.setUserId(0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> budgetValidator.validate(budget),
                "Expected exception to be thrown");

        assertEquals("Field 'userId' must be greater than 0", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenNameIsNull() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setId(1);
        budget.setUserId(1);
        budget.setName(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> budgetValidator.validate(budget),
                "Expected exception to be thrown");

        assertEquals("Field 'name' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenNameIsEmpty() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setId(1);
        budget.setUserId(1);
        budget.setName("");

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> budgetValidator.validate(budget),
                "Expected exception to be thrown");

        assertEquals("Field 'name' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenNameExceedsMaxLength() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setId(1);
        budget.setUserId(1);
        budget.setName("a".repeat(101)); // 101 characters

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> budgetValidator.validate(budget),
                "Expected exception to be thrown");

        assertEquals("Field 'name' must not exceed 100 characters", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenMaximumAllowedIsNull() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setId(1);
        budget.setUserId(1);
        budget.setName("Valid name");
        budget.setMaximumAllowed(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> budgetValidator.validate(budget),
                "Expected exception to be thrown");

        assertEquals("Field 'maximumAllowed' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenMaximumAllowedIsNegative() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setId(1);
        budget.setUserId(1);
        budget.setName("Valid name");
        budget.setMaximumAllowed(-1);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> budgetValidator.validate(budget),
                "Expected exception to be thrown");

        assertEquals("Field 'maximumAllowed' must be at least 0", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenIntervalIsNull() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setId(1);
        budget.setUserId(1);
        budget.setName("Valid name");
        budget.setMaximumAllowed(100);
        budget.setInterval(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> budgetValidator.validate(budget),
                "Expected exception to be thrown");

        assertEquals("Field 'interval' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenCurrentUsageIsNull() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setId(1);
        budget.setUserId(1);
        budget.setName("Valid name");
        budget.setMaximumAllowed(100);
        budget.setInterval(BudgetInterval.MONTHLY);
        budget.setCurrentUsage(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> budgetValidator.validate(budget),
                "Expected exception to be thrown");

        assertEquals("Field 'currentUsage' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenCurrentUsageIsNegative() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setId(1);
        budget.setUserId(1);
        budget.setName("Valid name");
        budget.setMaximumAllowed(100);
        budget.setInterval(BudgetInterval.MONTHLY);
        budget.setCurrentUsage(-1);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> budgetValidator.validate(budget),
                "Expected exception to be thrown");

        assertEquals("Field 'currentUsage' must be at least 0", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenTransactionIdIsNull() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setId(1);
        budget.setUserId(1);
        budget.setName("Valid name");
        budget.setMaximumAllowed(100);
        budget.setInterval(BudgetInterval.MONTHLY);
        budget.setCurrentUsage(10);
        budget.setTransactionId(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> budgetValidator.validate(budget),
                "Expected exception to be thrown");

        assertEquals("Field 'transactionId' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenTransactionIdIsLessThanOne() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setId(1);
        budget.setUserId(1);
        budget.setName("Valid name");
        budget.setMaximumAllowed(100);
        budget.setInterval(BudgetInterval.MONTHLY);
        budget.setCurrentUsage(10);
        budget.setTransactionId(0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> budgetValidator.validate(budget),
                "Expected exception to be thrown");

        assertEquals("Field 'transactionId' must be greater than 0", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenAccountIdIsNull() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setId(1);
        budget.setUserId(1);
        budget.setName("Valid name");
        budget.setMaximumAllowed(100);
        budget.setInterval(BudgetInterval.MONTHLY);
        budget.setCurrentUsage(10);
        budget.setTransactionId(1);
        budget.setAccountId(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> budgetValidator.validate(budget),
                "Expected exception to be thrown");

        assertEquals("Field 'accountId' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenAccountIdIsLessThanOne() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setId(1);
        budget.setUserId(1);
        budget.setName("Valid name");
        budget.setMaximumAllowed(100);
        budget.setInterval(BudgetInterval.MONTHLY);
        budget.setCurrentUsage(10);
        budget.setTransactionId(1);
        budget.setAccountId(0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> budgetValidator.validate(budget),
                "Expected exception to be thrown");

        assertEquals("Field 'accountId' must be greater than 0", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldDoNothingWhenAllFieldsAreValid() {
        // Given
        BudgetEntity budget = new BudgetEntity();
        budget.setId(1);
        budget.setUserId(1);
        budget.setName("Valid name");
        budget.setMaximumAllowed(100);
        budget.setInterval(BudgetInterval.MONTHLY);
        budget.setCurrentUsage(10);
        budget.setTransactionId(1);
        budget.setAccountId(1);

        // When & Then
        assertDoesNotThrow(() -> budgetValidator.validate(budget), "Expected no exception to be thrown");
    }
}
