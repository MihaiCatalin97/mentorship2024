package org.java.mentorship.andrei_s.persistence;

import org.java.mentorship.andrei_s.common.EntityRepository;
import org.java.mentorship.andrei_s.domain.Song;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SongRepository extends EntityRepository<Song> {

    SongRepository(RowMapper<Song> rowMapper, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate) {
        super(rowMapper, jdbcTemplate, namedJdbcTemplate, "songs");
    }

    @Override
    public Song createNew(Song song) {
        jdbcTemplate.update("INSERT INTO songs (name, style, duration, artist_id, album_id) VALUES (?, ?, ?, ?, ?)",
                song.getName(), song.getStyle(), song.getDuration(), song.getArtistId(), song.getAlbumId());

        return song;
    }

    @Override
    public Song updateById(Integer id, Song modifiedSong) {
        jdbcTemplate.update("UPDATE songs SET name = ?, style = ?, duration = ?, artist_id = ?, album_id = ? WHERE id = ?",
                modifiedSong.getName(), modifiedSong.getStyle(), modifiedSong.getDuration(), modifiedSong.getArtistId(), modifiedSong.getAlbumId(), id);

        return modifiedSong;
    }
}
