package org.java.mentorship.andrei_s.persistence;

import lombok.AllArgsConstructor;
import org.java.mentorship.andrei_s.domain.Artist;
import org.java.mentorship.andrei_s.domain.Song;
import org.java.mentorship.andrei_s.exception.EntityNotFound;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
@AllArgsConstructor
public class ArtistRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Artist> rowMapper;

    public Artist createNew(Artist artist) {
        jdbcTemplate.update("INSERT INTO artists (name) VALUES (?)",
                artist.getName());

        return artist;
    }

    public List<Artist> findAll() {
        return jdbcTemplate.query("SELECT * FROM artists", rowMapper);
    }

    public Artist getById(int id) {
        return jdbcTemplate.query("SELECT * FROM artists WHERE id = ?", rowMapper, id).getFirst();
    }

    public Artist updateById(int id, Artist modifiedArtist) {
        jdbcTemplate.update("UPDATE artists SET name = ? WHERE id = ?",
                modifiedArtist.getName(), modifiedArtist.getId());

        return modifiedArtist;
    }

    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM artists WHERE id = ?", id);
    }
}
