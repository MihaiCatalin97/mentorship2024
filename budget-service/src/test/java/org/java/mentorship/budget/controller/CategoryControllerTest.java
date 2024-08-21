package org.java.mentorship.budget.controller;

import org.java.mentorship.budget.domain.CategoryEntity;
import org.java.mentorship.budget.domain.mapper.CategoryContractMapper;
import org.java.mentorship.budget.service.CategoryService;
import org.java.mentorship.contracts.budget.dto.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private List<CategoryEntity> categoryEntities;

    @BeforeEach
    void setupMocks() {
        categoryEntities = Arrays.asList(
                CategoryEntity.builder().id(1).name("Travel").userId(100).build(),
                CategoryEntity.builder().id(2).name("Food").userId(100).build()
        );
    }

    @Test
    void getAllCategoriesShouldReturnContractsFromCategoryService() {
        when(categoryService.findAll()).thenReturn(categoryEntities);

        List<Category> categories = categoryEntities.stream()
                .map(CategoryContractMapper::entityToContract)
                .toList();

        ResponseEntity<List<Category>> response = categoryController.getAllCategories();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(categories.get(0).getId(), response.getBody().get(0).getId());
        assertEquals(categories.get(1).getName(), response.getBody().get(1).getName());
    }

    @Test
    void getCategoryByIdShouldReturnContractFromCategoryService() {
        when(categoryService.findById(anyInt())).thenReturn(categoryEntities.get(0));

        ResponseEntity<Category> response = categoryController.getCategoryById(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Travel", response.getBody().getName());
    }

    @Test
    void createCategoryShouldReturnCreatedCategory() {
        Category category = Category.builder().name("New Category").userId(100).build();
        CategoryEntity categoryEntity = CategoryContractMapper.contractToEntity(category);

        when(categoryService.save(any(CategoryEntity.class)))
                .thenReturn(CategoryEntity.builder().id(1).name("New Category").userId(100).build());

        ResponseEntity<Category> response = categoryController.createCategory(category);

        assertEquals(202, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("New Category", response.getBody().getName());
    }

    @Test
    void updateCategoryShouldReturnUpdatedCategory() {
        Category category = Category.builder().name("Updated Category").userId(100).build();
        CategoryEntity categoryEntity = CategoryContractMapper.contractToEntity(category);
        categoryEntity.setId(1);

        when(categoryService.update(any(CategoryEntity.class)))
                .thenReturn(CategoryEntity.builder().id(1).name("Updated Category").userId(100).build());

        ResponseEntity<Category> response = categoryController.updateCategory(1, category);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Updated Category", response.getBody().getName());
    }

    @Test
    void deleteCategoryShouldReturnDeletedCategory() {
        when(categoryService.delete(anyInt()))
                .thenReturn(CategoryEntity.builder().id(1).name("Deleted Category").userId(100).build());

        ResponseEntity<Category> response = categoryController.deleteCategory(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Deleted Category", response.getBody().getName());
    }

    @Test
    void getCategoryByIdShouldReturn404WhenCategoryNotFound() {
        when(categoryService.findById(anyInt())).thenThrow(new IllegalArgumentException("Category not found"));

        assertThrows(IllegalArgumentException.class, () -> categoryController.getCategoryById(999));
    }

    @Test
    void deleteCategoryShouldReturn404WhenCategoryNotFound() {
        when(categoryService.delete(anyInt())).thenThrow(new IllegalArgumentException("Category not found"));

        assertThrows(IllegalArgumentException.class, () -> categoryController.deleteCategory(999));
    }
}
