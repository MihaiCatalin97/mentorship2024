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

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@ContextConfiguration(classes = {
        SongRepository.class,
        SongRowMapper.class
})
@Sql({
        "classpath:sql/schema.sql",
        "classpath:sql/testData.sql"
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
        song.setArtistId(1);
        song.setAlbumId(1);

        // When
        songRepository.save(song);

        // Then
        List<Song> result = songRepository.findAll();
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(s -> "Test Song 3".equals(s.getName())));
    }

    @Test
    void findByIdShouldReturnCorrectSong() {
        // Given
        Song song = songRepository.findAll().get(0);

        // When
        Song result = songRepository.findById(song.getId());

        // Then
        assertNotNull(result);
        assertEquals(song.getId(), result.getId());
        assertEquals(song.getName(), result.getName());
        assertEquals(song.getStyle(), result.getStyle());
        assertEquals(song.getDuration(), result.getDuration());
    }

    @Test
    void updateShouldModifyExistingRecord() {
        // Given
        Song song = songRepository.findAll().get(0);
        song.setName("Updated Test Song");
        song.setArtistId(1);
        song.setAlbumId(1);

        // When
        songRepository.update(song);

        // Then
        Song updatedSong = songRepository.findById(song.getId());
        assertEquals("Updated Test Song", updatedSong.getName());
        assertEquals(1, updatedSong.getArtistId());
        assertEquals(1, updatedSong.getAlbumId());
    }

    @Test
    void deleteShouldRemoveRecordFromDatabase() {
        // Given
        Song song = songRepository.findAll().get(0);

        // When
        songRepository.delete(song.getId());

        // Then
        List<Song> results = songRepository.findAll();
        assertEquals(1, results.size());
        assertFalse(results.stream().anyMatch(s -> s.getId().equals(song.getId())));
    }
}
