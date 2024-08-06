package org.java.mentorship.persistence;

import org.java.mentorship.common.EntityRepository;
import org.java.mentorship.domain.Song;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class SongRepository extends EntityRepository<Song, Integer> {

    SongRepository(JdbcTemplate jdbcTemplate, RowMapper<Song> rowMapper) {
        super(jdbcTemplate, rowMapper, "songs");
    }


    public Song save(final Song song) {
        jdbcTemplate.update("INSERT INTO songs (name, style, duration) VALUES(?,?,?)",
                song.getName(), song.getStyle(), song.getDuration());

        return song;
    }

    public Song update(final Integer id, final Song song) {
        super.findById(id);
        jdbcTemplate.update("UPDATE songs SET name = ?, style = ?, duration = ?, artist_id = ?, album_id = ? WHERE id = ?",
                song.getName(), song.getStyle(), song.getDuration(), song.getArtistId(), song.getAlbumId(), song.getId());
        return song;
    }


}

