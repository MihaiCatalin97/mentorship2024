package org.java.mentorship.persistence;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.common.EntityRepository;
import org.java.mentorship.domain.Artist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArtistRepository implements EntityRepository<Artist, Integer> {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Artist> rowMapper;

    @Override
    public Artist save(final Artist artist) {
        jdbcTemplate.update("INSERT INTO artists (name) VALUES (?)",
                artist.getName());
        return artist;
    }

    @Override
    public List<Artist> findAll() {
        return jdbcTemplate.query("SELECT * FROM artists", rowMapper);
    }

    @Override
    public Artist findById(final Integer id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM artists WHERE id = ?",
                new Object[]{id},
                rowMapper
        );
    }

    @Override
    public Artist update(final Artist artist) {
        jdbcTemplate.update(
                "UPDATE artists SET name = ? WHERE id = ?",
                artist.getName(),
                artist.getId()
        );
        return artist;
    }

    @Override
    public Artist delete(final Integer id) {
        Artist artist = findById(id);
        if (artist != null) {
            jdbcTemplate.update("DELETE FROM artists WHERE id = ?", id);
        }
        return artist;
    }
}
