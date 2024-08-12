package org.java.mentorship.contracts.budget.dto;

import lombok.Data;

@Data
public class Account {

    private Integer id;
    private Integer userId;
    private String name;
    private AccountType type;
    private Integer balance;
    private String currency;

}
