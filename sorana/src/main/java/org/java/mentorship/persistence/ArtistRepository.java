package org.java.mentorship.persistence;

import lombok.AllArgsConstructor;
import org.java.mentorship.domain.Artist;

import org.java.mentorship.persistence.mapper.ArtistRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


import static org.java.mentorship.sql.SQLfindAll.*;

@Repository
@AllArgsConstructor
public class ArtistRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Artist> findAll(Map<String,Object> params) {
        String sql = "SELECT * FROM artists " + getSQL(params);

        return jdbcTemplate.query(sql,params.values().toArray(), new ArtistRowMapper());

    }

    public Artist findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM artists WHERE id = ?", new ArtistRowMapper(), id);
    }

    public Artist save(Artist artist) {
        jdbcTemplate.update("INSERT INTO artists (name) VALUES (?)",artist.getName());
        return artist;
    }

    public Artist update(final Integer id,Artist artist) {
        findById(id);
        jdbcTemplate.update("UPDATE artists SET name = ? WHERE id = ?", artist.getName(), artist.getId());
        return artist;
    }

    public boolean delete(int id) {
        findById(id);
        jdbcTemplate.update("DELETE FROM artists WHERE id = ?", id);
        return true;
    }
}
