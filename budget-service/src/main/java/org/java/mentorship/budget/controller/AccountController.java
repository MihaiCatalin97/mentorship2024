package org.java.mentorship.budget.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.java.mentorship.budget.domain.BankAccountEntity;
import org.java.mentorship.budget.domain.mapper.AccountContractMapper;
import org.java.mentorship.budget.service.AccountService;
import org.java.mentorship.contracts.budget.dto.Account;
import static org.springframework.http.HttpStatus.ACCEPTED;
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
        List<Account> accounts = accountService.findAll().stream()
                .map(AccountContractMapper::entityToContract)
                .collect(Collectors.toList());
        return ResponseEntity.ok(accounts);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Integer id) {
        BankAccountEntity bankAccountEntity = accountService.findById(id);
        Account account = AccountContractMapper.entityToContract(bankAccountEntity);
        return ResponseEntity.ok(account);
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody @Valid Account account) {
        BankAccountEntity bankAccountEntity = AccountContractMapper.contractToEntity(account);
        BankAccountEntity savedAccount = accountService.save(bankAccountEntity);
        Account createdAccount = AccountContractMapper.entityToContract(savedAccount);
        return ResponseEntity.status(ACCEPTED).body(createdAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") Integer id,
                                                 @RequestBody @Valid Account account) {
        BankAccountEntity bankAccountEntity = AccountContractMapper.contractToEntity(account);
        bankAccountEntity.setId(id);
        BankAccountEntity updatedAccount = accountService.update(bankAccountEntity);
        Account updatedAccountContract = AccountContractMapper.entityToContract(updatedAccount);
        return ResponseEntity.ok(updatedAccountContract);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable("id") Integer id) {
        BankAccountEntity deletedAccount = accountService.delete(id);
        Account account = AccountContractMapper.entityToContract(deletedAccount);
        return ResponseEntity.ok(account);
    }
}
