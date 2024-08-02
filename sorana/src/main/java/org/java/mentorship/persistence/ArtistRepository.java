package org.java.mentorship.persistence;

import lombok.AllArgsConstructor;
import org.java.mentorship.common.EntityRepository;
import org.java.mentorship.domain.Artist;

import org.java.mentorship.persistence.mapper.ArtistRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ArtistRepository extends EntityRepository<Artist, Integer> {

    public ArtistRepository(JdbcTemplate jdbcTemplate, RowMapper<Artist> rowMapper) {
        super(jdbcTemplate, rowMapper, "artists");
    }

    public Artist save(Artist artist) {
        jdbcTemplate.update("INSERT INTO artists (name) VALUES (?)",artist.getName());
        return artist;
    }

    public Artist update(final Integer id,Artist artist) {
        findById(id);
        jdbcTemplate.update("UPDATE artists SET name = ? WHERE id = ?", artist.getName(), artist.getId());
        return artist;
    }

}
