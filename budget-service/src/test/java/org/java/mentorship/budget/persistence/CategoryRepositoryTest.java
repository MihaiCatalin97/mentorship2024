package org.java.mentorship.budget.persistence;

import org.h2.tools.Server;
import org.java.mentorship.budget.domain.CategoryEntity;
import org.java.mentorship.budget.persistence.mapper.CategoryRowMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@ContextConfiguration(classes = {
        CategoryRepository.class,
        CategoryRowMapper.class
})
@Sql({
        "classpath:sql/schema.sql",
        "classpath:sql/testData.sql"
})
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeAll
    static void initTest() throws SQLException {
        // Start the H2 web server to make the database accessible via browser for debugging
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8089")
                .start();
    }

    @Test
    void findAllShouldReturnAllDatabaseEntries() {
        // When
        List<CategoryEntity> results = categoryRepository.findAll();

        // Then
        assertEquals(3, results.size());  // Adjust the expected size based on test data
    }

    @Test
    void saveShouldInsertIntoTheDatabase() {
        // Given
        CategoryEntity category = new CategoryEntity();
        category.setUserId(1);
        category.setName("New Category");

        // When
        categoryRepository.save(category);

        // Then
        List<CategoryEntity> result = categoryRepository.findAll();
        assertEquals(4, result.size());  // Adjust the expected size based on test data
        assertTrue(result.stream().anyMatch(c -> "New Category".equals(c.getName())));
    }

    @Test
    void findByIdShouldReturnCorrectCategory() {
        // Given
        CategoryEntity category = categoryRepository.findAll().get(0);

        // When
        CategoryEntity result = categoryRepository.findById(category.getId());

        // Then
        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
        assertEquals(category.getName(), result.getName());
        assertEquals(category.getUserId(), result.getUserId());
    }

    @Test
    void updateShouldModifyExistingRecord() {
        // Given
        CategoryEntity category = categoryRepository.findAll().get(0);
        category.setName("Updated Category Name");

        // When
        categoryRepository.update(category);

        // Then
        CategoryEntity updatedCategory = categoryRepository.findById(category.getId());
        assertEquals("Updated Category Name", updatedCategory.getName());
    }

    @Test
    void deleteShouldRemoveRecordFromDatabase() {
        // Given
        CategoryEntity category = categoryRepository.findAll().get(1);

        // When
        CategoryEntity deletedCategory = categoryRepository.delete(category.getId());

        // Then
        List<CategoryEntity> results = categoryRepository.findAll();
        assertEquals(2, results.size());  // Adjust the expected size based on test data
        assertFalse(results.stream().anyMatch(c -> c.getId().equals(deletedCategory.getId())));
    }
}
