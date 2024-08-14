package org.java.mentorship.contracts.budget.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Budget {

    @NotNull(message = "Field 'id' must not be null")
    @Min(value = 1, message = "Field 'id' must be greater than 0")
    private Integer id;

    @NotNull(message = "Field 'userId' must not be null")
    @Min(value = 1, message = "Field 'userId' must be greater than 0")
    private Integer userId;

    @NotEmpty(message = "Field 'name' must not be null or empty")
    @Size(max = 100, message = "Field 'name' must not exceed 100 characters")
    private String name;

    @NotNull(message = "Field 'maximumAllowed' must not be null")
    @Min(value = 0, message = "Field 'maximumAllowed' must be at least 0")
    private Integer maximumAllowed;

    @NotNull(message = "Field 'interval' must not be null")
    private BudgetInterval interval;

    @NotNull(message = "Field 'currentUsage' must not be null")
    @Min(value = 0, message = "Field 'currentUsage' must be at least 0")
    private Integer currentUsage;

    @NotNull(message = "Field 'transactionId' must not be null")
    @Min(value = 1, message = "Field 'transactionId' must be greater than 0")
    private Integer transactionId;

    @NotNull(message = "Field 'accountId' must not be null")
    @Min(value = 1, message = "Field 'accountId' must be greater than 0")
    private Integer accountId;
}
