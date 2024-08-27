package org.java.mentorship.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.budget.client.AccountFeignClient;
import org.java.mentorship.contracts.budget.dto.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.java.mentorship.gateway.security.authorization.UserAuthorization.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountFeignClient accountFeignClient;

    @GetMapping()
    ResponseEntity<List<Account>> getAccounts() {
        loggedInAsAnyUser();

        List<Account> results = accountFeignClient.getAccounts();

        return ResponseEntity.ok(filterResults(results, Account::getUserId));
    }

    @GetMapping("/{id}")
    ResponseEntity<Account> getAccountById(@PathVariable(name = "id") Integer id) {
        loggedInAsAnyUser();

        Account account = accountFeignClient.getAccountById(id);

        return filterResult(account, Account::getUserId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    ResponseEntity<Account> createAccount(@RequestBody Account account) {
        loggedInAsUser(account.getUserId());

        return ResponseEntity.ok(accountFeignClient.createAccount(account));
    }

    @PutMapping("/{id}")
    ResponseEntity<Account> updateAccount(@PathVariable(name = "id") Integer id, @RequestBody Account account) {
        loggedInAsUser(account.getUserId());

        return ResponseEntity.ok(accountFeignClient.updateAccount(id, account));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteAccount(@PathVariable(name = "id") Integer id) {
        Account account = accountFeignClient.getAccountById(id);

        loggedInAsUser(account.getUserId());
        accountFeignClient.deleteAccount(id);

        return ResponseEntity.noContent().build();
    }
}
