package org.java.mentorship.persistence;

import lombok.AllArgsConstructor;
import org.java.mentorship.domain.Album;
import org.java.mentorship.domain.Artist;
import org.java.mentorship.persistence.mapper.AlbumRowMapper;
import org.java.mentorship.persistence.mapper.ArtistRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class AlbumRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Album> findAll() {

        return jdbcTemplate.query("SELECT * FROM albums", new AlbumRowMapper());
    }
    public Album findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM albums WHERE id = ?", new AlbumRowMapper(), id);
    }
    public Album save(Album album) {
        jdbcTemplate.update("INSERT INTO albums (name) VALUES (?, ?)", album.getName());
        return album;
    }
    public Album update(final Integer id,Album album) {
        findById(id);
        jdbcTemplate.update("UPDATE albums SET name = ? WHERE id = ?", album.getName(), album.getId());
        return album;
    }
    public boolean delete(int id) {
        findById(id);
        jdbcTemplate.update("DELETE FROM albums WHERE id = ?", id);
        return true;
    }
}
