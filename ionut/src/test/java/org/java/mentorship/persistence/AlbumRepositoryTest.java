package org.java.mentorship.persistence;

import org.h2.tools.Server;
import org.java.mentorship.domain.Album;
import org.java.mentorship.persistence.mapper.AlbumRowMapper;
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
        AlbumRepository.class,
        AlbumRowMapper.class
})
@Sql({
        "classpath:sql/schema.sql",
        "classpath:sql/testData.sql"
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
    void findAllShouldReturnAllDatabaseEntries() {
        // When
        List<Album> results = albumRepository.findAll();

        // Then
        assertEquals(2, results.size());
    }

    @Test
    void saveShouldInsertIntoTheDatabase() {
        // Given
        Album album = new Album();
        album.setName("New Album");
        album.setArtistId(1);

        // When
        albumRepository.save(album);

        // Then
        List<Album> result = albumRepository.findAll();
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(a -> "New Album".equals(a.getName())));
    }

    @Test
    void findByIdShouldReturnCorrectAlbum() {
        // Given
        Album album = albumRepository.findAll().get(0);

        // When
        Album result = albumRepository.findById(album.getId());

        // Then
        assertNotNull(result);
        assertEquals(album.getId(), result.getId());
        assertEquals(album.getName(), result.getName());
        assertEquals(album.getArtistId(), result.getArtistId());
    }

    @Test
    void updateShouldModifyExistingRecord() {
        // Given
        Album album = albumRepository.findAll().get(0);
        album.setName("Updated Album Name");
        album.setArtistId(1);

        // When
        albumRepository.update(album);

        // Then
        Album updatedAlbum = albumRepository.findById(album.getId());
        assertEquals("Updated Album Name", updatedAlbum.getName());
        assertEquals(1, updatedAlbum.getArtistId());
    }

    @Test
    void deleteShouldRemoveRecordFromDatabase() {
        // Given
        Album album = albumRepository.findAll().get(0);

        // When
        albumRepository.delete(album.getId());

        // Then
        List<Album> results = albumRepository.findAll();
        assertEquals(1, results.size());
        assertFalse(results.stream().anyMatch(a -> a.getId().equals(album.getId())));
    }
}
