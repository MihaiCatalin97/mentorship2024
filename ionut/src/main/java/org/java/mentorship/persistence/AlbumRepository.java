package org.java.mentorship.persistence;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.common.EntityRepository;
import org.java.mentorship.domain.Album;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlbumRepository implements EntityRepository<Album, Integer> {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Album> rowMapper;

    @Override
    public Album save(final Album album) {
        jdbcTemplate.update("INSERT INTO albums (name, artist_id) VALUES (?, ?)",
                album.getName(), album.getArtistId());
        return album;
    }

    @Override
    public List<Album> findAll() {
        return jdbcTemplate.query("SELECT * FROM albums", rowMapper);
    }

    @Override
    public Album findById(final Integer id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM albums WHERE id = ?",
                new Object[]{id},  // Pass the ID here
                rowMapper
        );
    }

    @Override
    public Album update(final Album album) {
        jdbcTemplate.update(
                "UPDATE albums SET name = ?, artist_id = ? WHERE id = ?",
                album.getName(),
                album.getArtistId(),
                album.getId()
        );
        return album;
    }

    @Override
    public Album delete(final Integer id) {
        Album album = findById(id);
        if (album != null) {
            jdbcTemplate.update("DELETE FROM albums WHERE id = ?", id);
        }
        return album;
    }
}
