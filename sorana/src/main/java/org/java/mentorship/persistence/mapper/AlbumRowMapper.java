package org.java.mentorship.persistence.mapper;

import org.java.mentorship.domain.Album;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumRowMapper implements RowMapper<Album> {
    @Override
    public Album mapRow(ResultSet rs, int rowNum) throws SQLException {
        Album album = new Album();
        album.setId(rs.getInt("id"));
        album.setName(rs.getString("name"));
        return album;
    }
}