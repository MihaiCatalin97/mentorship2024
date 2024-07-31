package org.java.mentorship.andrei_s.persistence;

import lombok.AllArgsConstructor;
import org.java.mentorship.andrei_s.domain.Song;
import org.java.mentorship.andrei_s.exception.EntityNotFound;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
@AllArgsConstructor
public class SongRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Song> rowMapper;

    public Song createNew(Song song) {
        jdbcTemplate.update("INSERT INTO songs (name, style, duration, artist_id, album_id) VALUES (?, ?, ?, ?, ?)",
                song.getName(), song.getStyle(), song.getDuration(), song.getArtist_id(), song.getAlbum_id());

        return song;
    }

    public List<Song> findAll() {
        return jdbcTemplate.query("SELECT * FROM songs", rowMapper);
    }

    public List<Song> findArtistSongs(int artist_id) {
        return jdbcTemplate.query("SELECT * FROM songs WHERE artist_id = ?", rowMapper, artist_id);
    }

    public Song getById(int id) {
        return jdbcTemplate.query("SELECT * FROM songs WHERE id = ?", rowMapper, id).getFirst();
    }

    public Song updateById(int id, Song modifiedSong) {
        jdbcTemplate.update("UPDATE songs SET name = ?, style = ?, duration = ?, artist_id = ?, album_id = ? WHERE id = ?",
                modifiedSong.getName(), modifiedSong.getStyle(), modifiedSong.getDuration(), modifiedSong.getArtist_id(), modifiedSong.getAlbum_id(), modifiedSong.getId());

        return modifiedSong;
    }

    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM songs WHERE id = ?", id);
    }
}
