package org.java.mentorship.budget.persistence.mapper;

import org.java.mentorship.budget.domain.CategoryEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CategoryRowMapper implements RowMapper<CategoryEntity> {

    @Override
    public CategoryEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setId(rs.getInt("id"));
        categoryEntity.setUserId(rs.getInt("user_id"));
        categoryEntity.setName(rs.getString("name"));

        return categoryEntity;
    }
}
