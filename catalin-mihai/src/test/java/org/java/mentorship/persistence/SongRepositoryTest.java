package org.java.mentorship.persistence;

import org.h2.tools.Server;
import org.java.mentorship.domain.Song;
import org.java.mentorship.persistence.mapper.SongRowMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@ContextConfiguration(classes = {
        SongRepository.class,
        SongRowMapper.class
})
@Sql({
        "classpath:sql/schema.sql"
})
class SongRepositoryTest {

    @Autowired
    private SongRepository songRepository;

    @BeforeAll
    public static void initTest() throws SQLException {
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082")
                .start();
    }

    @Test
    @Sql({
            "classpath:sql/testData.sql"
    })
    void findAllShouldReturnAllDatabaseEntries() {
        // When
        List<Song> results = songRepository.findAll();

        // Then
        assertEquals(2, results.size());
    }

    @Test
    void saveShouldInsertIntoTheDatabase() {
        // Given
        Song song = new Song();
        song.setName("Test Song 3");
        song.setStyle("Test style");
        song.setDuration(120);

        // When
        songRepository.save(song);

        // Then
        List<Song> result = songRepository.findAll();

        assertEquals(1, result.size());
    }
}
