package org.java.mentorship.contracts.budget.client;

import org.java.mentorship.contracts.budget.dto.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "transactionClient", url = "${service.budget.url}")
public interface TransactionFeignClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/transactions"
    )
    List<Transaction> getTransactions();

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/transactions/{id}"
    )
    Transaction getTransactionById(@PathVariable("id") Integer id);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/transactions"
    )
    Transaction createTransaction(@RequestBody Transaction transaction);

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/transactions/{id}"
    )
    Transaction updateTransaction(@PathVariable("id") Integer id, @RequestBody Transaction transaction);

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/transactions/{id}"
    )
    void deleteTransaction(@PathVariable("id") Integer id);
}
