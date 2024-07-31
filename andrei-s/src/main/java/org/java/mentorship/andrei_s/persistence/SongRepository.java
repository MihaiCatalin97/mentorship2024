package org.java.mentorship.andrei_s.persistence;

import lombok.AllArgsConstructor;
import org.java.mentorship.andrei_s.domain.Song;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Song> findAll()
    {
        return jdbcTemplate.query("SELECT * FROM songs", rowMapper);
    }
}
