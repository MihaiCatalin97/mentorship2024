package org.java.mentorship.percistence;


import org.h2.tools.Server;
import org.java.mentorship.domain.Artist;
import org.java.mentorship.domain.Song;
import org.java.mentorship.persistence.ArtistRepository;
import org.java.mentorship.persistence.SongRepository;
import org.java.mentorship.persistence.mapper.ArtistRowMapper;
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
        ArtistRepository.class,
        ArtistRowMapper.class
})
class ArtistRepositoryTest {

    @Autowired
    private ArtistRepository artistRepository;

    @BeforeAll
    public static void initTest() throws SQLException {
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082")
                .start();
    }



    @Test
    @Sql({
            "classpath:testData.sql"
    })
    void saveShouldInsertIntoTheDatabase() {
        // Given
        Artist artist = new Artist(1,"smth" );

        // When
        artistRepository.save(artist);
        Map<String, Object> params = new HashMap<>();
        // Then
        List<Artist> result = artistRepository.find(params);

        assertEquals(2, result.size());
    }

    @Test
    @Sql({
            "classpath:testData.sql"
    })
    void updateShouldUpdateTheDatabase() {
        Integer id = 1;
        Artist artist = new Artist(1,"smth");

        artist.setName("smth1");

        Artist  artist1 = artistRepository.update(id,artist);

        assertEquals("smth1",artist1.getName());
    }

    @Test
    @Sql({
            "classpath:testData.sql"
    })
    void deleteShouldDeleteFromTheDatabase() {
        Artist artist = new Artist(1,"smth" );

        boolean result =artistRepository.delete(artist.getId());

        assertTrue(result);
    }


    //??????
    @Test
    @Sql({
            "classpath:testData.sql"
    })
    void findByIdShouldReturnTheDatabaseEntry() {
        Artist artist = artistRepository.findById(1);

        assertEquals("Test Song 1",artist.getName());
    }
}