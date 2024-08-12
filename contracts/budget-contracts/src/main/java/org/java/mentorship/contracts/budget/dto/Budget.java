package org.java.mentorship.contracts.budget.dto;

import lombok.Data;

import java.util.List;

@Data
public class Budget {

    private Integer id;
    private Integer userId;
    private String name;
    private Integer maximumAllowed;
    private BudgetInterval interval;
    private Integer currentUsage;
    private List<Account> accounts;
    private List<Transaction> transactions;

}
