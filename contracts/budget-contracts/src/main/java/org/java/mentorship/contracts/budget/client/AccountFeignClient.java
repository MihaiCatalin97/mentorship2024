package org.java.mentorship.contracts.budget.client;

import org.java.mentorship.contracts.budget.dto.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "accountClient", url = "${service.budget.url}")
public interface AccountFeignClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/accounts"
    )
    List<Account> getAccounts();

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/accounts/{id}"
    )
    Account getAccountById(@PathVariable("id") Integer id);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/accounts"
    )
    Account createAccount(@RequestBody Account account);

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/accounts/{id}"
    )
    Account updateAccount(@PathVariable("id") Integer id, @RequestBody Account account);

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/accounts/{id}"
    )
    void deleteAccount(@PathVariable("id") Integer id);
}
