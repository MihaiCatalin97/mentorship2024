package org.java.mentorship.andrei_s.persistence.mapper;

import org.java.mentorship.andrei_s.domain.Song;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SongRowMapper implements RowMapper<Song> {

    @Override
    public Song mapRow(ResultSet rs, int rowNum) throws SQLException {
        Song song = new Song();

        song.setId(rs.getInt("id"));
        song.setName(rs.getString("name"));
        song.setStyle(rs.getString("style"));
        song.setDuration(rs.getInt("duration"));
        song.setArtist_id(rs.getInt("artist_id"));
        song.setAlbum_id(rs.getInt("album_id"));

        return song;
    }

}
