package org.java.mentorship.budget.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.java.mentorship.budget.domain.AccountEntity;
import org.java.mentorship.budget.domain.mapper.AccountContractMapper;
import org.java.mentorship.budget.service.AccountService;
import org.java.mentorship.contracts.budget.dto.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<AccountEntity> accountEntities = accountService.findAll();
        List<Account> accounts = accountEntities.stream()
                .map(AccountContractMapper::entityToContract)
                .collect(Collectors.toList());
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Integer id) {
        AccountEntity accountEntity = accountService.findById(id);
        Account account = AccountContractMapper.entityToContract(accountEntity);
        return ResponseEntity.ok(account);
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody @Valid Account account) {
        AccountEntity accountEntity = AccountContractMapper.contractToEntity(account);
        AccountEntity savedAccount = accountService.save(accountEntity);
        Account createdAccount = AccountContractMapper.entityToContract(savedAccount);
        return ResponseEntity.status(201).body(createdAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") Integer id,
                                                 @RequestBody @Valid Account account) {
        AccountEntity accountEntity = AccountContractMapper.contractToEntity(account);
        accountEntity.setId(id);
        AccountEntity updatedAccount = accountService.update(accountEntity);
        Account updatedAccountContract = AccountContractMapper.entityToContract(updatedAccount);
        return ResponseEntity.ok(updatedAccountContract);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable("id") Integer id) {
        AccountEntity deletedAccount = accountService.delete(id);
        if (deletedAccount == null) {
            return ResponseEntity.notFound().build();
        }
        Account deletedAccountContract = AccountContractMapper.entityToContract(deletedAccount);
        return ResponseEntity.ok(deletedAccountContract);
    }

}
