package org.java.mentorship.percistence;

import org.h2.tools.Server;
import org.java.mentorship.domain.Album;
import org.java.mentorship.domain.Artist;
import org.java.mentorship.persistence.AlbumRepository;
import org.java.mentorship.persistence.ArtistRepository;
import org.java.mentorship.persistence.mapper.AlbumRowMapper;
import org.java.mentorship.persistence.mapper.ArtistRowMapper;
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
        AlbumRepository.class,
        AlbumRowMapper.class
})
class AlbumRepositoryTest {

    @Autowired
    private AlbumRepository albumRepository;

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
        Album album = new Album(1,"smth", 1);

        // When
        albumRepository.save(album);
        Map<String, Object> params = new HashMap<>();
        // Then
        List<Album> result = albumRepository.find(params);

        assertEquals(2, result.size());
    }

    @Test
    @Sql({
            "classpath:testData.sql"
    })
    void updateShouldUpdateTheDatabase() {
        Integer id = 1;
        Album album = new Album(1,"smth", 1);

        album.setName("smth1");

        Album  album1 = albumRepository.update(id,album);

        assertEquals("smth1",album1.getName());
    }

    @Test
    @Sql({
            "classpath:testData.sql"
    })
    void deleteShouldDeleteFromTheDatabase() {
        Album album = new Album(1,"smth", 1);

        boolean result =albumRepository.delete(album.getId());

        assertTrue(result);
    }



//    @Test
//    @Sql({
//            "classpath:testData.sql"
//    })
//    void findByIdShouldReturnTheDatabaseEntry() {
//        Album album = albumRepository.findById(1);
//
//        assertEquals("astroworld",album.getName());
//    }
}
