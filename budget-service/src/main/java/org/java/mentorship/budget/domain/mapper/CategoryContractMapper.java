package org.java.mentorship.budget.domain.mapper;

import org.java.mentorship.budget.domain.CategoryEntity;
import org.java.mentorship.contracts.budget.dto.Category;

public class CategoryContractMapper {

    // Converts CategoryEntity to Category DTO
    public static Category entityToContract(CategoryEntity categoryEntity) {
        Category categoryContract = new Category();

        categoryContract.setId(categoryEntity.getId());
        categoryContract.setName(categoryEntity.getName());
        categoryContract.setUserId(categoryEntity.getUserId());

        return categoryContract;
    }

    // Converts Category DTO to CategoryEntity
    public static CategoryEntity contractToEntity(Category categoryContract) {
        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setId(categoryContract.getId());
        categoryEntity.setName(categoryContract.getName());
        categoryEntity.setUserId(categoryContract.getUserId());

        return categoryEntity;
    }
}
