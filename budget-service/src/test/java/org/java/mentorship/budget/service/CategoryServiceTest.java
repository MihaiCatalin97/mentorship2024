package org.java.mentorship.budget.service;

import org.java.mentorship.budget.domain.CategoryEntity;
import org.java.mentorship.budget.exception.NoEntityFoundException;
import org.java.mentorship.budget.persistence.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository repository;

    @Test
    void saveShouldValidateAndSaveCategory() {
        CategoryEntity categoryEntity = new CategoryEntity();
        when(repository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);

        CategoryEntity result = categoryService.save(categoryEntity);

        assertEquals(categoryEntity, result);
        verify(repository).save(categoryEntity);
    }

    @Test
    void findAllShouldCallRepository() {
        List<CategoryEntity> categories = Arrays.asList(new CategoryEntity(), new CategoryEntity());
        when(repository.findAll()).thenReturn(categories);

        List<CategoryEntity> result = categoryService.findAll();

        assertEquals(categories, result);
        verify(repository).findAll();
    }

    @Test
    void findByIdShouldReturnCategoryWhenExists() {
        CategoryEntity categoryEntity = new CategoryEntity();
        when(repository.findById(1)).thenReturn(categoryEntity);

        CategoryEntity result = categoryService.findById(1);

        assertEquals(categoryEntity, result);
        verify(repository).findById(1);
    }

    @Test
    void findByIdShouldThrowExceptionWhenNotFound() {
        when(repository.findById(1)).thenReturn(null);

        assertThrows(NoEntityFoundException.class, () -> categoryService.findById(1));
        verify(repository).findById(1);
    }

    @Test
    void updateShouldValidateAndUpdateCategoryWhenExists() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1);
        when(repository.findById(1)).thenReturn(categoryEntity);
        when(repository.update(any(CategoryEntity.class))).thenReturn(categoryEntity);

        CategoryEntity result = categoryService.update(categoryEntity);

        assertEquals(categoryEntity, result);
        verify(repository).findById(1);
        verify(repository).update(categoryEntity);
    }

    @Test
    void updateShouldThrowExceptionWhenCategoryNotFound() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1);
        when(repository.findById(1)).thenReturn(null);

        assertThrows(NoEntityFoundException.class, () -> categoryService.update(categoryEntity));
        verify(repository).findById(1);
        verify(repository, never()).update(any(CategoryEntity.class));
    }

    @Test
    void deleteShouldReturnDeletedCategoryWhenExists() {
        CategoryEntity categoryEntity = new CategoryEntity();
        when(repository.findById(1)).thenReturn(categoryEntity);
        when(repository.delete(1)).thenReturn(categoryEntity);

        CategoryEntity result = categoryService.delete(1);

        assertEquals(categoryEntity, result);
        verify(repository).findById(1);
        verify(repository).delete(1);
    }

    @Test
    void deleteShouldThrowExceptionWhenCategoryNotFound() {
        when(repository.findById(1)).thenReturn(null);

        assertThrows(NoEntityFoundException.class, () -> categoryService.delete(1));
        verify(repository).findById(1);
        verify(repository, never()).delete(1);
    }
}
