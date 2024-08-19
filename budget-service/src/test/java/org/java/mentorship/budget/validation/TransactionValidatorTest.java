package org.java.mentorship.budget.validation;

import org.java.mentorship.budget.domain.TransactionEntity;
import org.java.mentorship.budget.exception.FieldIsNullException;
import org.java.mentorship.contracts.budget.dto.TransactionType;
import org.junit.jupiter.api.*;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionValidatorTest {

    private TransactionValidator transactionValidator;

    @BeforeEach
    void initialize() {
        transactionValidator = new TransactionValidator();
    }

    @Test
    void validateShouldThrowExceptionWhenIdIsNull() {
        // Given
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> transactionValidator.validate(transaction),
                "Expected exception to be thrown");

        assertEquals("Field 'id' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenIdIsLessThanOne() {
        // Given
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transactionValidator.validate(transaction),
                "Expected exception to be thrown");

        assertEquals("Field 'id' must be greater than 0", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenUserIdIsNull() {
        // Given
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(1);
        transaction.setUserId(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> transactionValidator.validate(transaction),
                "Expected exception to be thrown");

        assertEquals("Field 'userId' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenUserIdIsLessThanOne() {
        // Given
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(1);
        transaction.setUserId(0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transactionValidator.validate(transaction),
                "Expected exception to be thrown");

        assertEquals("Field 'userId' must be greater than 0", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenTypeIsNull() {
        // Given
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(1);
        transaction.setUserId(1);
        transaction.setType(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> transactionValidator.validate(transaction),
                "Expected exception to be thrown");

        assertEquals("Field 'type' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenValueIsNull() {
        // Given
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(1);
        transaction.setUserId(1);
        transaction.setType(TransactionType.EXPENSE);
        transaction.setValue(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> transactionValidator.validate(transaction),
                "Expected exception to be thrown");

        assertEquals("Field 'value' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenValueIsNegative() {
        // Given
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(1);
        transaction.setUserId(1);
        transaction.setType(TransactionType.EXPENSE);
        transaction.setValue(-1);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transactionValidator.validate(transaction),
                "Expected exception to be thrown");

        assertEquals("Field 'value' must be at least 0", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenDescriptionIsNullOrEmpty() {
        // Given
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(1);
        transaction.setUserId(1);
        transaction.setType(TransactionType.EXPENSE);
        transaction.setValue(100);
        transaction.setDescription(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> transactionValidator.validate(transaction),
                "Expected exception to be thrown");

        assertEquals("Field 'description' is null", exception.getMessage(), "Unexpected exception message");

        // Test for empty description
        transaction.setDescription("");
        exception = assertThrows(FieldIsNullException.class,
                () -> transactionValidator.validate(transaction),
                "Expected exception to be thrown");

        assertEquals("Field 'description' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenDescriptionExceedsMaxLength() {
        // Given
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(1);
        transaction.setUserId(1);
        transaction.setType(TransactionType.EXPENSE);
        transaction.setValue(100);
        transaction.setDescription("a".repeat(256));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transactionValidator.validate(transaction),
                "Expected exception to be thrown");

        assertEquals("Field 'description' must not exceed 255 characters", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenAccountIdIsNull() {
        // Given
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(1);
        transaction.setUserId(1);
        transaction.setType(TransactionType.EXPENSE);
        transaction.setValue(100);
        transaction.setDescription("Valid description");
        transaction.setAccountId(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> transactionValidator.validate(transaction),
                "Expected exception to be thrown");

        assertEquals("Field 'accountId' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenAccountIdIsLessThanOne() {
        // Given
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(1);
        transaction.setUserId(1);
        transaction.setType(TransactionType.EXPENSE);
        transaction.setValue(100);
        transaction.setDescription("Valid description");
        transaction.setAccountId(0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transactionValidator.validate(transaction),
                "Expected exception to be thrown");

        assertEquals("Field 'accountId' must be greater than 0", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenTimestampIsNull() {
        // Given
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(1);
        transaction.setUserId(1);
        transaction.setType(TransactionType.EXPENSE);
        transaction.setValue(100);
        transaction.setDescription("Valid description");
        transaction.setAccountId(1);
        transaction.setTimestamp(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> transactionValidator.validate(transaction),
                "Expected exception to be thrown");

        assertEquals("Field 'timestamp' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenTimestampIsInTheFuture() {
        // Given
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(1);
        transaction.setUserId(1);
        transaction.setType(TransactionType.EXPENSE);
        transaction.setValue(100);
        transaction.setDescription("Valid description");
        transaction.setAccountId(1);
        transaction.setTimestamp(OffsetDateTime.now().plusDays(1));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transactionValidator.validate(transaction),
                "Expected exception to be thrown");

        assertEquals("Field 'timestamp' cannot be in the future", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldDoNothingWhenAllFieldsAreValid() {
        // Given
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(1);
        transaction.setUserId(1);
        transaction.setType(TransactionType.EXPENSE);
        transaction.setValue(100);
        transaction.setDescription("Valid description");
        transaction.setAccountId(1);
        transaction.setTimestamp(OffsetDateTime.now().minusDays(1));

        // When & Then
        assertDoesNotThrow(() -> transactionValidator.validate(transaction), "Expected no exception to be thrown");
    }
}
