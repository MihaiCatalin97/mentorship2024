package org.java.mentorship.budget.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.java.mentorship.budget.domain.CategoryEntity;
import org.java.mentorship.budget.domain.mapper.CategoryContractMapper;
import org.java.mentorship.budget.service.CategoryService;
import org.java.mentorship.contracts.budget.dto.Category;
import static org.springframework.http.HttpStatus.ACCEPTED;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAll().stream()
                .map(CategoryContractMapper::entityToContract)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Integer id) {
        CategoryEntity categoryEntity = categoryService.findById(id);
        Category category = CategoryContractMapper.entityToContract(categoryEntity);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody @Valid Category category) {
        CategoryEntity categoryEntity = CategoryContractMapper.contractToEntity(category);
        CategoryEntity savedCategory = categoryService.save(categoryEntity);
        Category createdCategory = CategoryContractMapper.entityToContract(savedCategory);
        return ResponseEntity.status(ACCEPTED).body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Integer id,
                                                   @RequestBody @Valid Category category) {
        CategoryEntity categoryEntity = CategoryContractMapper.contractToEntity(category);
        categoryEntity.setId(id);
        CategoryEntity updatedCategory = categoryService.update(categoryEntity);
        Category updatedCategoryContract = CategoryContractMapper.entityToContract(updatedCategory);
        return ResponseEntity.ok(updatedCategoryContract);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") Integer id) {
        CategoryEntity deletedCategory = categoryService.delete(id);
        Category category = CategoryContractMapper.entityToContract(deletedCategory);
        return ResponseEntity.ok(category);
    }
}
