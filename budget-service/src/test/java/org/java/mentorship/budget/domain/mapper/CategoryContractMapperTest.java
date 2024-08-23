package org.java.mentorship.budget.domain.mapper;

import org.java.mentorship.budget.domain.CategoryEntity;
import org.java.mentorship.contracts.budget.dto.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryContractMapperTest {

    @Test
    void categoryContractMapperClassInstantiation() {
        CategoryContractMapper mapper = new CategoryContractMapper();
        assertNotNull(mapper);
    }

    @Test
    void entityToContractShouldMapFieldsCorrectly() {
        // Given
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(1)
                .name("Travel")
                .userId(100)
                .build();

        // When
        Category categoryContract = CategoryContractMapper.entityToContract(categoryEntity);

        // Then
        assertNotNull(categoryContract);
        assertEquals(1, categoryContract.getId());
        assertEquals("Travel", categoryContract.getName());
        assertEquals(100, categoryContract.getUserId());
    }

    @Test
    void contractToEntityShouldMapFieldsCorrectly() {
        // Given
        Category categoryContract = Category.builder()
                .id(1)
                .name("Travel")
                .userId(100)
                .build();

        // When
        CategoryEntity categoryEntity = CategoryContractMapper.contractToEntity(categoryContract);

        // Then
        assertNotNull(categoryEntity);
        assertEquals(1, categoryEntity.getId());
        assertEquals("Travel", categoryEntity.getName());
        assertEquals(100, categoryEntity.getUserId());
    }
}
