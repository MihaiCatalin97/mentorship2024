package org.java.mentorship.andrei_s.persistence;

import lombok.AllArgsConstructor;
import org.java.mentorship.andrei_s.domain.Album;
import org.java.mentorship.andrei_s.domain.Artist;
import org.java.mentorship.andrei_s.exception.EntityNotFound;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
@AllArgsConstructor
public class AlbumRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Album> rowMapper;

    public Album createNew(Album album) {
        jdbcTemplate.update("INSERT INTO albums (name) VALUES (?)",
                album.getName());

        return album;
    }

    public List<Album> findAll() {
        return jdbcTemplate.query("SELECT * FROM albums", rowMapper);
    }

    public Album getById(int id) {
        try {
            return jdbcTemplate.query("SELECT * FROM albums WHERE id = ?", rowMapper, id).getFirst();
        } catch (NoSuchElementException e) {
            throw new EntityNotFound(id);
        }
    }

    public List<Album> getArtistAlbums(int artist_id) {
        return jdbcTemplate.query("SELECT * FROM albums WHERE artist_id = ?", rowMapper, artist_id);
    }

    public Album updateById(int id, Album modifiedAlbum) {
        // Check if the song exists, otherwise it will throw EntityNotFound
        getById(id);

        jdbcTemplate.update("UPDATE albums SET name = ? WHERE id = ?",
                modifiedAlbum.getName(), modifiedAlbum.getId());

        return modifiedAlbum;
    }

    public boolean deleteById(int id) {
        // Check if the song exists, otherwise it will throw EntityNotFound
        getById(id);

        jdbcTemplate.update("DELETE FROM albums WHERE id = ?", id);

        return true;
    }
}
