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
//        jdbcTemplate.update("INSERT INTO songs (id, name, style, duration, artistId, albumId) VALUES(?,?,?,?,?,?)",
//                song.getId(),song.getName(), song.getStyle(), song.getDuration(),song.getArtistId(), song.getAlbumId());

        jdbcTemplate.update("INSERT INTO songs (name, style, duration) VALUES(?,?,?)",
                song.getName(), song.getStyle(), song.getDuration());


        return song;
    }
}