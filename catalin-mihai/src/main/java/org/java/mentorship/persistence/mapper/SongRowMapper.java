package org.java.mentorship.persistence.mapper;

import org.java.mentorship.domain.Song;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SongRowMapper implements RowMapper<Song> {

    @Override
    public Song mapRow(ResultSet rs,
                       int rowNum) throws SQLException {
        Song song = new Song();

        song.setId(rs.getInt("id"));
        song.setName(rs.getString("name"));
        song.setStyle(rs.getString("style"));
        song.setDuration(rs.getInt("duration"));
        song.setArtistId(rs.getInt("artist_id"));
        song.setAlbumId(rs.getInt("album_id"));

        return song;
    }
}
