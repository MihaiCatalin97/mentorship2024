package org.java.mentorship.percistence;


import org.h2.tools.Server;
import org.java.mentorship.domain.Song;
import org.java.mentorship.persistence.SongRepository;

import org.java.mentorship.persistence.mapper.SongRowMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@ContextConfiguration(classes = {
        SongRepository.class,
        SongRowMapper.class
})
class SongRepositoryTest {

    @Autowired
    private SongRepository songRepository;

    @BeforeAll
    public static void initTest() throws SQLException {
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8081")
                .start();
    }

    @Test
    @Sql({
            "classpath:testData.sql"
    })
    void findAllShouldReturnAllDatabaseEntries() {
        Map<String, Object> params = new HashMap<>();
        // When
        List<Song> results = songRepository.find(params);

        // Then
        assertEquals(1, results.size());
    }

    @Test
    @Sql({
            "classpath:testData.sql"
    })
    void saveShouldInsertIntoTheDatabase() {
        // Given
        Song song = new Song(1,"smth" ,"smbd" ,3,1,1);

        // When
        songRepository.save(song);
        Map<String, Object> params = new HashMap<>();
        // Then
        List<Song> result = songRepository.find(params);

        assertEquals(2, result.size());
    }

    @Test
    @Sql({
            "classpath:testData.sql"
    })
    void updateShouldUpdateTheDatabase() {
        Integer id = 1;
        Song song = new Song(1,"smth" ,"smbd" ,3,null,null);

        song.setName("smth1");

        Song  song1 = songRepository.update(id,song);

        assertEquals("smth1",song1.getName());
    }

    @Test
    @Sql({
            "classpath:testData.sql"
    })
    void deleteShouldDeleteFromTheDatabase() {
        Song song = new Song(1,"smth" ,"smbd" ,3,1,1);
        boolean result = songRepository.delete(song.getId());
        assertTrue(result);
    }

//    @Test
//    @Sql({
//            "classpath:testData.sql"
//    })
//    void findByIdShouldReturnTheDatabaseEntry() {
//        Song song1 = songRepository.findById(1);
//        assertEquals("Test Song 1",song1.getName());
//    }
}
