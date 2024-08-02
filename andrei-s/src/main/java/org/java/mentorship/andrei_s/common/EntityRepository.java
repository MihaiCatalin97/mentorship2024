package org.java.mentorship.andrei_s.common;

import org.java.mentorship.andrei_s.sql.SQLConditions;
import org.java.mentorship.andrei_s.sql.common.SQLOperator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class EntityRepository<T extends Entity> {
    protected final RowMapper<T> rowMapper;
    protected final JdbcTemplate jdbcTemplate;
    protected final NamedParameterJdbcTemplate namedJdbcTemplate;
    protected final String tableName;

    public EntityRepository(RowMapper<T> rowMapper, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate, String tableName)
    {
        this.rowMapper = rowMapper;
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbcTemplate = namedJdbcTemplate;
        this.tableName = tableName;
    }

    public List<T> find(Map<String, Object> filters) {
        MapSqlParameterSource in = new MapSqlParameterSource();
        SQLConditions sqlConditions = new SQLConditions(new SQLOperator("TRUE"));

        filters.values().removeAll(Collections.singleton(null));
        filters.forEach(in::addValue);
        filters.replaceAll((k, v) -> ":" + k);
        sqlConditions.andEquals(filters);

        return namedJdbcTemplate.query(String.format("SELECT * FROM " + this.tableName + " WHERE %s", sqlConditions.build()), in, rowMapper);
    }

    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM " + this.tableName + " WHERE id = ?", id);
    };

    public T getById(Integer id) {
        return jdbcTemplate.query("SELECT * FROM " + this.tableName + " WHERE id = ?", rowMapper, id).getFirst();
    }

    public abstract T createNew(T entity);
    public abstract T updateById(Integer id, T modifiedEntity);
}
