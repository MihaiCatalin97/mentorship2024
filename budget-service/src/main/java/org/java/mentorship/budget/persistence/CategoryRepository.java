package org.java.mentorship.budget.persistence;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.budget.domain.CategoryEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<CategoryEntity> rowMapper;

    public CategoryEntity save(final CategoryEntity categoryEntity) {
        jdbcTemplate.update(
                "INSERT INTO categories (user_id, name) VALUES(?,?)",
                categoryEntity.getUserId(),
                categoryEntity.getName()
        );
        return categoryEntity;
    }

    public List<CategoryEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM categories", rowMapper);
    }

    public CategoryEntity findById(final Integer id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM categories WHERE id = ?",
                new Object[]{id},
                rowMapper
        );
    }

    public CategoryEntity update(final CategoryEntity categoryEntity) {
        jdbcTemplate.update(
                "UPDATE categories SET name = ? WHERE id = ?",
                categoryEntity.getName(),
                categoryEntity.getId()
        );
        return categoryEntity;
    }

    public CategoryEntity delete(final Integer id) {
        CategoryEntity categoryEntity = findById(id);
        jdbcTemplate.update("DELETE FROM categories WHERE id = ?", id);
        return categoryEntity;
    }
}
