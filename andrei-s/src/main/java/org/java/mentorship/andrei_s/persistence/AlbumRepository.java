package org.java.mentorship.andrei_s.persistence;

import lombok.AllArgsConstructor;
import org.java.mentorship.andrei_s.domain.Album;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        return jdbcTemplate.query("SELECT * FROM albums WHERE id = ?", rowMapper, id).getFirst();
    }

    public List<Album> findByArtist(int artist_id) {
        return jdbcTemplate.query("SELECT * FROM albums WHERE artist_id = ?", rowMapper, artist_id);
    }

    public Album updateById(int id, Album modifiedAlbum) {
        jdbcTemplate.update("UPDATE albums SET name = ? WHERE id = ?",
                modifiedAlbum.getName(), id);

        return modifiedAlbum;
    }

    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM albums WHERE id = ?", id);
    }
}
