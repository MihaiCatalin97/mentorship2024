package org.java.mentorship.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.budget.client.CategoryFeignClient;
import org.java.mentorship.contracts.budget.dto.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.java.mentorship.gateway.security.authorization.UserIdAuthorization.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryFeignClient categoryFeignClient;

    @GetMapping()
    ResponseEntity<List<Category>> getCategories() {
        loggedInAsAnyUser();

        return ResponseEntity.ok(filterResults(categoryFeignClient.getCategories(), Category::getUserId));
    }

    @GetMapping("/{id}")
    ResponseEntity<Category> getCategoryById(@PathVariable(name = "id") Integer id) {
        loggedInAsAnyUser();

        Category category = categoryFeignClient.getCategoryById(id);

        return filterResult(category, Category::getUserId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    ResponseEntity<Category> createCategory(@RequestBody Category category) {
        loggedInAsUser(category.getUserId());

        return ResponseEntity.ok(categoryFeignClient.createCategory(category));
    }

    @PutMapping("/{id}")
    ResponseEntity<Category> updateCategory(@PathVariable(name = "id") Integer id, @RequestBody Category category) {
        loggedInAsUser(category.getUserId());

        return ResponseEntity.ok(categoryFeignClient.updateCategory(id, category));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCategory(@PathVariable(name = "id") Integer id) {
        Category category = categoryFeignClient.getCategoryById(id);

        loggedInAsUser(category.getUserId());
        categoryFeignClient.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}
