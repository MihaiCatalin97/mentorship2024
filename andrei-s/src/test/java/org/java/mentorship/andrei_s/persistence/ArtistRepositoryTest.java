package org.java.mentorship.andrei_s.persistence;

import org.assertj.core.api.Assertions;
import org.java.mentorship.andrei_s.domain.Artist;
import org.java.mentorship.andrei_s.persistence.mapper.ArtistRowMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;

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
    void createNewShouldInsertIntoTheDatabase() {
        Map<String, Object> filter = new HashMap<>();
        filter.put("name", "UniqueArtistName");
        Artist artist = new Artist();
        artist.setName("UniqueArtistName");

        artistRepository.createNew(artist);

        List<Artist> result = artistRepository.find(filter);
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void deleteByIdShouldThrowWhenAlbumContainsSongs() {
        Assertions.assertThatThrownBy(() -> artistRepository.deleteById(1))
                .hasMessageContaining("FK_ALBUM_ARTIST");
    }

    @Test
    void updateByIdShouldUpdateEntity() {
        Artist artist = artistRepository.getById(3);
        artist.setName("New Artist 3");

        artistRepository.updateById(3, artist);
        Artist updatedArtist = artistRepository.getById(3);

        Assertions.assertThat(updatedArtist).extracting("name").isEqualTo("New Artist 3");
    }

    @Test
    void findShouldReturnAllEntities() {
        List<Artist> artists = artistRepository.find(new HashMap<>());

        Assertions.assertThat(artists.size()).isEqualTo(3);
    }

    @Test
    void findShouldFilterEntities() {
        Map<String, Object> filter = new HashMap<>();
        filter.put("name", "Artist 3");

        List<Artist> artists = artistRepository.find(filter);

        Assertions.assertThat(artists.size()).isEqualTo(1);
    }

    @Test
    void getByIdShouldReturnEntityWhenIdExists() {
        Artist artist = artistRepository.getById(3);

        Assertions.assertThat(artist)
                .extracting("name")
                .isEqualTo("Artist 3");
    }

    @Test
    void getByIdShouldThrowWhenIdDoesNotExist() {
        Assertions.assertThatThrownBy(() -> artistRepository.getById(10));
    }
}
