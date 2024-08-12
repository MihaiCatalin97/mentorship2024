package org.java.mentorship.contracts.budget.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Transaction {

    private Integer id;
    private Integer userId;
    private TransactionType type;
    private Integer value;
    private Integer sourceAccountId;
    private Integer toAccountId;
    private String description;
    private LocalDateTime timestamp;
}
