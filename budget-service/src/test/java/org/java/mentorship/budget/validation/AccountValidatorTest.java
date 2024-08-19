package org.java.mentorship.budget.validation;

import org.java.mentorship.budget.domain.BankAccountEntity;
import org.java.mentorship.budget.exception.FieldIsNullException;
import org.java.mentorship.contracts.budget.dto.AccountType;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class AccountValidatorTest {

    private AccountValidator accountValidator;

    @BeforeEach
    void initialize() {
        accountValidator = new AccountValidator();
    }

    @Test
    void validateShouldThrowExceptionWhenIdIsNull() {
        // Given
        BankAccountEntity account = new BankAccountEntity();
        account.setId(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> accountValidator.validate(account),
                "Expected exception to be thrown");

        assertEquals("Field 'id' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenUserIdIsNull() {
        // Given
        BankAccountEntity account = new BankAccountEntity();
        account.setId(1);
        account.setUserId(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> accountValidator.validate(account),
                "Expected exception to be thrown");

        assertEquals("Field 'userId' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenNameIsNullOrEmpty() {
        // Given
        BankAccountEntity account = new BankAccountEntity();
        account.setId(1);
        account.setUserId(1);
        account.setName(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> accountValidator.validate(account),
                "Expected exception to be thrown");

        assertEquals("Field 'name' is null", exception.getMessage(), "Unexpected exception message");

        // Test for empty name
        account.setName("");
        exception = assertThrows(FieldIsNullException.class,
                () -> accountValidator.validate(account),
                "Expected exception to be thrown");

        assertEquals("Field 'name' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenTypeIsNull() {
        // Given
        BankAccountEntity account = new BankAccountEntity();
        account.setId(1);
        account.setUserId(1);
        account.setName("Account");
        account.setType(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> accountValidator.validate(account),
                "Expected exception to be thrown");

        assertEquals("Field 'type' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenBalanceIsNull() {
        // Given
        BankAccountEntity account = new BankAccountEntity();
        account.setId(1);
        account.setUserId(1);
        account.setName("Account");
        account.setType(AccountType.SAVINGS);
        account.setBalance(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> accountValidator.validate(account),
                "Expected exception to be thrown");

        assertEquals("Field 'balance' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenBalanceIsNegative() {
        // Given
        BankAccountEntity account = new BankAccountEntity();
        account.setId(1);
        account.setUserId(1);
        account.setName("Account");
        account.setType(AccountType.SAVINGS);
        account.setBalance(-1);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> accountValidator.validate(account),
                "Expected exception to be thrown");

        assertEquals("Field 'balance' must be at least 0", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldThrowExceptionWhenCurrencyIsNullOrEmpty() {
        // Given
        BankAccountEntity account = new BankAccountEntity();
        account.setId(1);
        account.setUserId(1);
        account.setName("Account");
        account.setType(AccountType.SAVINGS);
        account.setBalance(100);
        account.setCurrency(null);

        // When & Then
        FieldIsNullException exception = assertThrows(FieldIsNullException.class,
                () -> accountValidator.validate(account),
                "Expected exception to be thrown");

        assertEquals("Field 'currency' is null", exception.getMessage(), "Unexpected exception message");

        // Test for empty currency
        account.setCurrency("");
        exception = assertThrows(FieldIsNullException.class,
                () -> accountValidator.validate(account),
                "Expected exception to be thrown");

        assertEquals("Field 'currency' is null", exception.getMessage(), "Unexpected exception message");
    }

    @Test
    void validateShouldDoNothingWhenAllFieldsAreValid() {
        // Given
        BankAccountEntity account = new BankAccountEntity();
        account.setId(1);
        account.setUserId(1);
        account.setName("Account");
        account.setType(AccountType.SAVINGS);
        account.setBalance(100);
        account.setCurrency("USD");

        // When & Then
        assertDoesNotThrow(() -> accountValidator.validate(account), "Expected no exception to be thrown");
    }
}
