package org.java.mentorship.andrei_s.persistence;

import org.assertj.core.api.Assertions;
import org.java.mentorship.andrei_s.domain.Artist;
import org.java.mentorship.andrei_s.domain.Song;
import org.java.mentorship.andrei_s.persistence.mapper.ArtistRowMapper;
import org.java.mentorship.andrei_s.persistence.mapper.SongRowMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JdbcTest
@ContextConfiguration(classes = {
        ArtistRepository.class,
        ArtistRowMapper.class
})
@ExtendWith(MockitoExtension.class)
public class ArtistRepositoryTest {

    @Autowired
    ArtistRepository artistRepository;

    @Test
    void saveShouldInsertIntoTheDatabase() {
        // Given
        Map<String, Object> filter = new HashMap<>();
        filter.put("name", "UniqueArtistName");
        Artist artist = new Artist();
        artist.setName("UniqueArtistName");

        // When
        artistRepository.createNew(artist);

        // Then
        List<Artist> result = artistRepository.find(filter);
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void deleteByIdShouldDeleteEntity() {
        // Given
        Map<String, Object> filter = new HashMap<>();
        filter.put("name", "UniqueArtistName");
        Artist artist = new Artist();
        artist.setName("UniqueArtistName");

        // When
        artistRepository.createNew(artist);
        Artist artistFromDb = artistRepository.find(filter).getFirst();
        artistRepository.deleteById(artistFromDb.getId());

        // Then
        List<Artist> result = artistRepository.find(filter);
        Assertions.assertThat(result.size()).isEqualTo(0);
    }
    @Test
    void updateByIdShouldUpdateEntity() {
        // Given
        Map<String, Object> filter = new HashMap<>();
        filter.put("name", "UniqueArtistName");
        Artist artist = new Artist();
        artist.setName("UniqueArtistName");

        // When
        artistRepository.createNew(artist);
        Artist artistFromDb = artistRepository.find(filter).getFirst();
        artist.setName("NewUniqueName");
        artistRepository.updateById(artistFromDb.getId(), artist);

        // Then
        List<Artist> result = artistRepository.find(filter);
        Assertions.assertThat(result.size()).isEqualTo(0);
        filter.put("name", "NewUniqueName");
        List<Artist> result2 = artistRepository.find(filter);
        Assertions.assertThat(result2.size()).isEqualTo(1);
    }

}
