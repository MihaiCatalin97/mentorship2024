package org.java.mentorship.persistence.mapper;

import org.java.mentorship.domain.Album;
import org.java.mentorship.domain.Song;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AlbumRowMapper implements RowMapper<Album> {

    @Override
    public Album mapRow(ResultSet rs,
                        int rowNum) throws SQLException {
        Album album = new Album();

        album.setId(rs.getInt("id"));
        album.setName(rs.getString("name"));
        album.setArtistId(rs.getInt("artist_id"));

        return album;

    }
}
