package org.java.mentorship.andrei_s.persistence;

import lombok.AllArgsConstructor;
import org.java.mentorship.andrei_s.domain.Song;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class SongRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final RowMapper<Song> rowMapper;

    public Song createNew(Song song) {
        jdbcTemplate.update("INSERT INTO songs (name, style, duration, artist_id, album_id) VALUES (?, ?, ?, ?, ?)",
                song.getName(), song.getStyle(), song.getDuration(), song.getArtistId(), song.getAlbumId());

        return song;
    }

    public List<Song> find(String style, Integer artistId, Integer albumId) {
        MapSqlParameterSource in = new MapSqlParameterSource();
        StringBuilder whereFilter = new StringBuilder();
        whereFilter.append("1=1");
        // aici s-a incercat 70 de metode, n a mers in niciun fel
        if (!Objects.isNull(style)) {
            in.addValue("style", style);
            whereFilter.append(" AND style = :style");
        }
        if (!Objects.isNull(artistId)) {
            in.addValue("artistId", artistId);
            whereFilter.append(" AND artist_id = :artistId");
        }
        if (!Objects.isNull(albumId)) {
            in.addValue("albumId", albumId);
            whereFilter.append(" AND album_id = :albumId");
        }

        return namedJdbcTemplate.query(String.format("SELECT * FROM songs WHERE (%s)", whereFilter), in, rowMapper);
    }

    public List<Song> findByArtist(int albumId) {
        return jdbcTemplate.query("SELECT * FROM songs WHERE albumId = ?", rowMapper, albumId);
    }

    public List<Song> findByAlbum(int albumId) {
        return jdbcTemplate.query("SELECT * FROM songs WHERE albumId = ?", rowMapper, albumId);
    }

    public Song getById(int id) {
        return jdbcTemplate.query("SELECT * FROM songs WHERE id = ?", rowMapper, id).getFirst();
    }

    public Song updateById(int id, Song modifiedSong) {
        jdbcTemplate.update("UPDATE songs SET name = ?, style = ?, duration = ?, artist_id = ?, album_id = ? WHERE id = ?",
                modifiedSong.getName(), modifiedSong.getStyle(), modifiedSong.getDuration(), modifiedSong.getArtistId(), modifiedSong.getAlbumId(), id);

        return modifiedSong;
    }

    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM songs WHERE id = ?", id);
    }
}
