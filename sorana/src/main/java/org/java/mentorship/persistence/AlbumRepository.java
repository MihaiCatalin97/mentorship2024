package org.java.mentorship.persistence;

import lombok.AllArgsConstructor;
import org.java.mentorship.common.EntityRepository;
import org.java.mentorship.domain.Album;
import org.java.mentorship.domain.Artist;
import org.java.mentorship.persistence.mapper.AlbumRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;



@Repository
public class AlbumRepository extends EntityRepository<Album, Integer> {


    public AlbumRepository(JdbcTemplate jdbcTemplate, RowMapper<Album> rowMapper) {
        super(jdbcTemplate, rowMapper, "albums");
    }

    public Album save(Album album) {
        jdbcTemplate.update("INSERT INTO albums (name) VALUES (?)", album.getName());
        return album;
    }
    public Album update(final Integer id,Album album) {
        findById(id);
        jdbcTemplate.update("UPDATE albums SET name = ? WHERE id = ?", album.getName(), album.getId());
        return album;
    }
}
