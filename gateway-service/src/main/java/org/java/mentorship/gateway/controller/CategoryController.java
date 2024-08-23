package org.java.mentorship.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.budget.client.CategoryFeignClient;
import org.java.mentorship.contracts.budget.dto.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryFeignClient categoryFeignClient;

    @GetMapping()
    ResponseEntity<List<Category>> getCategories() {
        // TODO: Add any necessary restrictions or filters for retrieving categories
        return ResponseEntity.ok(categoryFeignClient.getCategories());
    }

    @GetMapping("/{id}")
    ResponseEntity<Category> getCategoryById(@PathVariable(name = "id") Integer id) {
        // TODO: Add any necessary authorization checks or validations
        return ResponseEntity.ok(categoryFeignClient.getCategoryById(id));
    }

    @PostMapping()
    ResponseEntity<Category> createCategory(@RequestBody Category category) {
        // TODO: Add any necessary validation or pre-processing before creating the category
        return ResponseEntity.ok(categoryFeignClient.createCategory(category));
    }

    @PutMapping("/{id}")
    ResponseEntity<Category> updateCategory(@PathVariable(name = "id") Integer id, @RequestBody Category category) {
        // TODO: Add any necessary validation or pre-processing before updating the category
        return ResponseEntity.ok(categoryFeignClient.updateCategory(id, category));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCategory(@PathVariable(name = "id") Integer id) {
        // TODO: Add any necessary checks or processing before deleting the category
        categoryFeignClient.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
