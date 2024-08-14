package org.java.mentorship.budget.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.java.mentorship.contracts.budget.dto.AccountType;

@Data
public class AccountEntity {

    @NotNull(message = "Field 'id' must not be null")
    @Min(value = 1, message = "Field 'id' must be greater than 0")
    private Integer id;

    @NotNull(message = "Field 'userId' must not be null")
    @Min(value = 1, message = "Field 'userId' must be greater than 0")
    private Integer userId;

    @NotEmpty(message = "Field 'name' must not be null or empty")
    @Size(max = 100, message = "Field 'name' must not exceed 100 characters")
    private String name;

    @NotNull(message = "Field 'type' must not be null")
    private AccountType type;

    @NotNull(message = "Field 'balance' must not be null")
    @Min(value = 0, message = "Field 'balance' must be at least 0")
    private Integer balance;

    @NotEmpty(message = "Field 'currency' must not be null or empty")
    private String currency;
}
