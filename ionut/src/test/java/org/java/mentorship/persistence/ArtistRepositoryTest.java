package org.java.mentorship.persistence;

import org.h2.tools.Server;
import org.java.mentorship.domain.Artist;
import org.java.mentorship.persistence.mapper.ArtistRowMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@ContextConfiguration(classes = {
        ArtistRepository.class,
        ArtistRowMapper.class
})
@Sql({
        "classpath:sql/schema.sql",
        "classpath:sql/testData.sql"
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
    void findAllShouldReturnAllDatabaseEntries() {
        // When
        List<Artist> results = artistRepository.findAll();

        // Then
        assertEquals(2, results.size());
    }

    @Test
    void saveShouldInsertIntoTheDatabase() {
        // Given
        Artist artist = new Artist();
        artist.setName("New Artist");

        // When
        artistRepository.save(artist);

        // Then
        List<Artist> result = artistRepository.findAll();
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(a -> "New Artist".equals(a.getName())));
    }

    @Test
    void findByIdShouldReturnCorrectArtist() {
        // Given
        Artist artist = artistRepository.findAll().get(0);

        // When
        Artist result = artistRepository.findById(artist.getId());

        // Then
        assertNotNull(result);
        assertEquals(artist.getId(), result.getId());
        assertEquals(artist.getName(), result.getName());
    }

    @Test
    void updateShouldModifyExistingRecord() {
        // Given
        Artist artist = artistRepository.findAll().get(0);
        artist.setName("Updated Artist");

        // When
        artistRepository.update(artist);

        // Then
        Artist updatedArtist = artistRepository.findById(artist.getId());
        assertEquals("Updated Artist", updatedArtist.getName());
    }

    @Test
    void deleteShouldRemoveRecordFromDatabase() {
        // Given
        List<Artist> artistsBeforeDelete = artistRepository.findAll();
        assertFalse(artistsBeforeDelete.isEmpty(), "No artists found for testing.");

        Artist artist = artistsBeforeDelete.get(0);

        // When
        artistRepository.delete(artist.getId());

        // Then
        List<Artist> artistsAfterDelete = artistRepository.findAll();
        assertEquals(artistsBeforeDelete.size() - 1, artistsAfterDelete.size());
        assertFalse(artistsAfterDelete.stream().anyMatch(a -> a.getId().equals(artist.getId())));
    }
}
