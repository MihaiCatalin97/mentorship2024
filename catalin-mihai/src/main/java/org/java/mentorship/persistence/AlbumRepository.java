package org.java.mentorship.persistence;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.common.EntityRepository;
import org.java.mentorship.domain.Album;
import org.java.mentorship.domain.Song;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlbumRepository implements EntityRepository<Album, Integer> {

    private final JdbcTemplate jdbcTemplate;

}
