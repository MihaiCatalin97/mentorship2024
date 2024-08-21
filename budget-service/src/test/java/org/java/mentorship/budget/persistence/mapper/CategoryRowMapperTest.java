package org.java.mentorship.budget.persistence.mapper;

import org.java.mentorship.budget.domain.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryRowMapperTest {

    private final CategoryRowMapper rowMapper = new CategoryRowMapper();

    @Test
    void shouldMapRowToCategoryEntity() throws SQLException {
        // Given
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        // Mocking the behavior of ResultSet
        Mockito.when(resultSet.getInt("id")).thenReturn(1);
        Mockito.when(resultSet.getInt("user_id")).thenReturn(2);
        Mockito.when(resultSet.getString("name")).thenReturn("Travel");

        // When
        CategoryEntity categoryEntity = rowMapper.mapRow(resultSet, 1);

        // Then
        assertEquals(1, categoryEntity.getId());
        assertEquals(2, categoryEntity.getUserId());
        assertEquals("Travel", categoryEntity.getName());
    }
}
