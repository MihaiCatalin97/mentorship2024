package org.java.mentorship.persistence;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.common.EntityRepository;
import org.java.mentorship.domain.Album;
import org.java.mentorship.domain.Artist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArtistRepository implements EntityRepository<Artist, Integer> {

    private final JdbcTemplate jdbcTemplate;

}
