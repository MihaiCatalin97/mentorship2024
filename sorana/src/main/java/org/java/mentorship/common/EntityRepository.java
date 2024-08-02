package org.java.mentorship.common;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;

import static org.java.mentorship.sql.SQLfindAll.*;

public class EntityRepository<T, I>{

    protected JdbcTemplate jdbcTemplate;
    protected RowMapper<T> rowMapper;
    protected String tableName;

    public EntityRepository(JdbcTemplate jdbcTemplate, RowMapper<T> rowMapper, String tableName) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.tableName = tableName;
    }

    public boolean delete(Integer id) {
       jdbcTemplate.update("DELETE FROM " + tableName + " WHERE id = ?", id);
       return true;
   }

   public List<T> find(Map<String, Object> params) {
       String sql = "SELECT * FROM " + tableName + " " + getSQL(params);

       return jdbcTemplate.query(sql, params.values().toArray(),rowMapper);
   }
   public T findById(I id) {
       return jdbcTemplate.queryForObject("SELECT * FROM " + tableName + " WHERE id = ?", rowMapper, id);
   }
}
