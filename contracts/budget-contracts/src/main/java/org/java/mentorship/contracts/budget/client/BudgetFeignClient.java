package org.java.mentorship.contracts.budget.client;

import org.java.mentorship.contracts.budget.dto.Budget;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "budgetClient",
        url = "${service.budget.url}")
public interface BudgetFeignClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/budgets"
    )
    List<Budget> getBudgets();

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/budgets/{id}"
    )
    Budget getBudgetById(@PathVariable("id") Integer id);

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/budgets",
            params = "userId"
    )
    List<Budget> getBudgetsByUserId(@RequestParam("userId") Integer userId);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/budgets"
    )
    Budget createBudget(@RequestBody Budget budget);

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/budgets/{id}"
    )
    Budget updateBudget(@PathVariable("id") Integer id, @RequestBody Budget budget);

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/budgets/{id}"
    )
    void deleteBudget(@PathVariable("id") Integer id);
}
