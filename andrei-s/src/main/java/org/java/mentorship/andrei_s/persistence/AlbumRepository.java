package org.java.mentorship.andrei_s.persistence;

import org.java.mentorship.andrei_s.common.EntityRepository;
import org.java.mentorship.andrei_s.domain.Album;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class AlbumRepository extends EntityRepository<Album> {
    AlbumRepository(RowMapper<Album> rowMapper, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate) {
        super(rowMapper, jdbcTemplate, namedJdbcTemplate, "albums");
    }

    @Override
    public Album createNew(Album album) {
        jdbcTemplate.update("INSERT INTO albums (name) VALUES (?)",
                album.getName());

        return album;
    }

    @Override
    public Album updateById(Integer id, Album modifiedAlbum) {
        jdbcTemplate.update("UPDATE albums SET name = ? WHERE id = ?",
                modifiedAlbum.getName(), id);

        return modifiedAlbum;
    }
}
