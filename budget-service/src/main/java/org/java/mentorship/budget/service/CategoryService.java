package org.java.mentorship.budget.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.domain.CategoryEntity;
import org.java.mentorship.budget.exception.UnauthorizedException;
import org.java.mentorship.budget.persistence.CategoryRepository;
import org.java.mentorship.budget.exception.NoEntityFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryEntity save(final CategoryEntity categoryEntity) {
        return repository.save(categoryEntity);
    }

    public List<CategoryEntity> findAll() {
        return repository.findAll();
    }

    public CategoryEntity findById(final Integer id) {
        CategoryEntity categoryEntity = repository.findById(id);
        if (categoryEntity == null) {
            throw new NoEntityFoundException("Category with id " + id + " not found");
        }
        return categoryEntity;
    }

    public CategoryEntity update(final CategoryEntity categoryEntity) {
        CategoryEntity existingCategory = repository.findById(categoryEntity.getId());
        if (!Objects.equals(categoryEntity.getUserId(), existingCategory.getUserId())) {
            throw new UnauthorizedException("You can't edit the user id field of this entity");
        }
        if (existingCategory == null) {
            throw new NoEntityFoundException("Category with id " + categoryEntity.getId() + " not found");
        }
        return repository.update(categoryEntity);
    }

    public CategoryEntity delete(final Integer id) {
        CategoryEntity categoryEntity = repository.findById(id);
        if (categoryEntity == null) {
            throw new NoEntityFoundException("Category with id " + id + " not found");
        }
        repository.delete(id);
        return categoryEntity;
    }
}
