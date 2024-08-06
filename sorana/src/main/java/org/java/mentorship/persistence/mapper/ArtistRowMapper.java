package org.java.mentorship.persistence.mapper;

import org.java.mentorship.domain.Artist;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ArtistRowMapper implements RowMapper<Artist> {
    @Override
    public Artist mapRow(ResultSet rs, int rowNum) throws SQLException {
        Artist artist = new Artist();
        artist.setId(rs.getInt("id"));
        artist.setName(rs.getString("name"));
        return artist;
    }
}
