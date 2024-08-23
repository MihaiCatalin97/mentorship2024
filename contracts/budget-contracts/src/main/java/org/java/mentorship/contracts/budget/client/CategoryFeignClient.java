package org.java.mentorship.contracts.budget.client;

import org.java.mentorship.contracts.budget.dto.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "categoryClient", url = "${service.budget.url}")
public interface CategoryFeignClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/categories"
    )
    List<Category> getCategories();

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/categories/{id}"
    )
    Category getCategoryById(@PathVariable("id") Integer id);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/categories"
    )
    Category createCategory(@RequestBody Category category);

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/categories/{id}"
    )
    Category updateCategory(@PathVariable("id") Integer id, @RequestBody Category category);

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/categories/{id}"
    )
    void deleteCategory(@PathVariable("id") Integer id);
}
