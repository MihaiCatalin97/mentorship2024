package org.java.mentorship.andrei_s.persistence;

import org.java.mentorship.andrei_s.common.EntityRepository;
import org.java.mentorship.andrei_s.domain.Artist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ArtistRepository extends EntityRepository<Artist> {

    ArtistRepository(RowMapper<Artist> rowMapper, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate) {
        super(rowMapper, jdbcTemplate, namedJdbcTemplate, "artists");
    }

    @Override
    public Artist createNew(Artist artist) {
        jdbcTemplate.update("INSERT INTO artists (name) VALUES (?)",
                artist.getName());

        return artist;
    }
    @Override
    public Artist updateById(Integer id, Artist modifiedArtist) {
        jdbcTemplate.update("UPDATE artists SET name = ? WHERE id = ?",
                modifiedArtist.getName(), id);

        return modifiedArtist;
    }
}
