package org.java.mentorship.persistence;

import lombok.AllArgsConstructor;
import org.java.mentorship.domain.Artist;
import org.java.mentorship.persistence.mapper.ArtistRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ArtistRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Artist> findAll() {
        return jdbcTemplate.query("SELECT * FROM artists", new ArtistRowMapper());
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
