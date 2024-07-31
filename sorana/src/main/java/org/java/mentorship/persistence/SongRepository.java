package org.java.mentorship.persistence;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.common.EntityRepository;
import org.java.mentorship.domain.Song;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class SongRepository implements EntityRepository<Song, Integer> {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Song> rowMapper;

    @Override
    public Song save(final Song song) {
        jdbcTemplate.update("INSERT INTO songs (name, style, duration) VALUES(?,?,?)",
                song.getName(), song.getStyle(), song.getDuration());

        return song;
    }
//    @Override
//    public Song create(final Song song) {
//        jdbcTemplate.update("INSERT INTO songs (name, style, duration,artistId,albumId) VALUES(?,?,?,?,?)",
//                song.getName(), song.getStyle(), song.getDuration(), song.getArtistId(), song.getAlbumId());
//
//        return song;
//    }
    @Override
    public Song findById(final Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM songs WHERE id = ?", rowMapper, id);
    }

    @Override
    public Song update(final Integer id, final Song song){
        findById(id);
        jdbcTemplate.update("UPDATE songs SET name = ?, style = ?, duration = ?, artist_id = ?, album_id = ? WHERE id = ?",
                song.getName(), song.getStyle(), song.getDuration(), song.getArtistId(), song.getAlbumId(), song.getId());
        return song;
    }

    @Override
    public boolean delete(final Integer id) {
        findById(id);
        jdbcTemplate.update("DELETE FROM songs WHERE id = ?", id);
        return true;
    }

    @Override
    public List<Song> findAll() {
        return jdbcTemplate.query("SELECT * FROM songs", rowMapper);
    }
}
