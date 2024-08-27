package org.java.mentorship.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.budget.client.AccountFeignClient;
import org.java.mentorship.contracts.budget.dto.Account;
import org.java.mentorship.gateway.security.authorization.UserIdAuthorization;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountFeignClient accountFeignClient;

    @GetMapping()
    ResponseEntity<List<Account>> getAccounts() {
        // TODO: Add any necessary restrictions or filters for retrieving accounts
        return ResponseEntity.ok(accountFeignClient.getAccounts());
    }

    @GetMapping("/{id}")
    ResponseEntity<Account> getAccountById(@PathVariable(name = "id") Integer id) {
        // TODO: Add any necessary authorization checks or validations
        return ResponseEntity.ok(accountFeignClient.getAccountById(id));
    }

    @PostMapping()
    ResponseEntity<Account> createAccount(@RequestBody Account account) {
        UserIdAuthorization.loggedInAsUser(account.getUserId());
        return ResponseEntity.ok(accountFeignClient.createAccount(account));
    }

    @PutMapping("/{id}")
    ResponseEntity<Account> updateAccount(@PathVariable(name = "id") Integer id, @RequestBody Account account) {
        UserIdAuthorization.loggedInAsUser(account.getUserId());
        return ResponseEntity.ok(accountFeignClient.updateAccount(id, account));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteAccount(@PathVariable(name = "id") Integer id) {
        // TODO: Add any necessary checks or processing before deleting the account
        accountFeignClient.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
