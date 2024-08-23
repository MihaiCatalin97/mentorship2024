package org.java.mentorship.contracts.budget.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @NotNull(message = "Field 'id' must not be null")
    @Min(value = 1, message = "Field 'id' must be greater than 0")
    private Integer id;

    @NotNull(message = "Field 'userId' must not be null")
    @Min(value = 1, message = "Field 'userId' must be greater than 0")
    private Integer userId;

    @NotNull(message = "Field 'type' must not be null")
    private TransactionType type;

    @NotNull(message = "Field 'value' must not be null")
    @Min(value = 0, message = "Field 'value' must be at least 0")
    private Integer value;

    @NotEmpty(message = "Field 'description' must not be null or empty")
    @Size(max = 255, message = "Field 'description' must not exceed 255 characters")
    private String description;

    @NotNull(message = "Field 'categoryId' must not be null")
    @Min(value = 1, message = "Field 'categoryId' must be greater than 0")
    private Integer categoryId;

    @NotNull(message = "Field 'accountId' must not be null")
    @Min(value = 1, message = "Field 'accountId' must be greater than 0")
    private Integer accountId;

    @NotNull(message = "Field 'timestamp' must not be null")
    private OffsetDateTime timestamp;
}
