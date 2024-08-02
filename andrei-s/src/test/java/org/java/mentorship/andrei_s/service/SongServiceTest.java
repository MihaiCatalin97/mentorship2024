package org.java.mentorship.andrei_s.service;

import org.java.mentorship.andrei_s.domain.Song;
import org.java.mentorship.andrei_s.exception.EntityNotFound;
import org.java.mentorship.andrei_s.exception.FieldIsNullException;
import org.java.mentorship.andrei_s.persistence.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SongServiceTest {

    SongService songService;

    private final static Song songEmpty = new Song();
    private final static Song songNullName = new Song(null, null, "Style", 2, 2, 2);
    private final static Song goodSong = new Song(null, "Name", "Style", 2, 2, 2);

    @Mock
    SongRepository songRepository;

    @BeforeEach
    public void init() {
        songService = new SongService(songRepository);
    }

    @Test
    void getByIdShouldThrowWhenNotFound() {
        int songId = 404;

        when(songRepository.getById(songId)).thenAnswer(invocationOnMock -> {
            throw new NoSuchElementException();
        });

        assertThatThrownBy(() -> songService.getById(songId))
                .isInstanceOf(EntityNotFound.class)
                .hasMessageContaining("404")
                .hasMessageContaining("song");
    }
    @Test
    void getByIdShouldReturnSongWhenFound() {
        int songId = 200;

        when(songRepository.getById(songId)).thenAnswer(invocationOnMock -> {
            int id = invocationOnMock.getArgument(0);
            Song song = new Song();
            song.setId(id);
            return song;
        });

        assertThat(songService.getById(songId))
                .isInstanceOf(Song.class)
                .extracting("id")
                .isEqualTo(songId);
    }
    @Test
    void findShouldReturnFilteredSongs() {
        Map<String, Object> filters = new HashMap<>();
        filters.put("style", "Style");
        filters.put("album_id", 1);
        filters.put("artist_id", 1);

        when(songRepository.find(filters)).thenAnswer(invocationOnMock -> {
            Map<String, Object> suppliedFilters = invocationOnMock.getArgument(0);
            Song song = new Song();
            Song song2 = new Song();
            List<Song> songs = new ArrayList<>();
            song.setId(123);
            song.setStyle((String) suppliedFilters.get("style"));
            song.setAlbumId((Integer) suppliedFilters.get("album_id"));
            song.setArtistId((Integer) suppliedFilters.get("artist_id"));
            song2.setId(124);
            song2.setStyle((String) "Style2");
            song2.setAlbumId((Integer) suppliedFilters.get("album_id"));
            song2.setArtistId((Integer) suppliedFilters.get("artist_id"));
            songs.add(song);
            songs.add(song2);
            return songs;
        });

        List<Song> result = songService.find(filters);

        verify(songRepository, times(1)).find(filters);
        assertThat(result.getFirst())
                .extracting("style", "artistId", "albumId")
                .containsExactly("Style", 1, 1);

    }
    @Test
    void findShouldReturnAllSongs() {
        when(songRepository.find(anyMap())).thenAnswer(invocationOnMock -> {
            Map<String, Object> suppliedFilters = invocationOnMock.getArgument(0);
            Song song = new Song();
            Song song2 = new Song();
            List<Song> songs = new ArrayList<>();
            song.setId(123);
            song.setStyle((String) "Style");
            song.setAlbumId(1);
            song.setArtistId(2);
            song2.setId(124);
            song2.setStyle((String) "Style2");
            song2.setAlbumId(1);
            song2.setArtistId(2);
            songs.add(song);
            songs.add(song2);
            return songs;
        });

        List<Song> result = songService.find();

        verify(songRepository, times(1)).find(anyMap());
        assertThat(result.size()).isEqualTo(2);

    }
    @Test
    void createNewShouldValidate() {
        assertThatThrownBy(() -> songService.createNew(songEmpty)).isInstanceOf(FieldIsNullException.class);
        assertThatThrownBy(() -> songService.createNew(songNullName)).isInstanceOf(FieldIsNullException.class);
    }
    @Test
    void creteNewShouldReturnSong() {
        when(songRepository.createNew(any())).thenAnswer(invocationOnMock -> {
            Song song = invocationOnMock.getArgument(0);

            return Song.builder()
                    .id(1)
                    .name(song.getName())
                    .artistId(song.getArtistId())
                    .albumId(song.getAlbumId())
                    .style(song.getStyle())
                    .duration(song.getDuration())
                    .build();
        });

        Song result = songService.createNew(goodSong);

        assertThat(result)
                .extracting("id", "name").isNotNull()
                .containsExactly(1, goodSong.getName());
    }
    @Test
    void deleteByIdShouldVerifyId() {
        songService.deleteById(1);
        verify(songRepository).deleteById(1);
    }
    @Test
    void deleteByIdShouldCallRepo() {
        songService.deleteById(1);
        verify(songRepository).getById(1);
    }
    @Test
    void updateByIdShouldValidate() {
        assertThatThrownBy(() -> songService.updateById(1, songNullName)).isInstanceOf(FieldIsNullException.class);
    }
    @Test
    void updateByIdShouldCallRepo() {
        songService.updateById(1, goodSong);
        verify(songRepository).updateById(1, goodSong);
    }
    @Test
    void updateByIdShouldVerifyId() {
        songService.updateById(1, goodSong);
        verify(songRepository).getById(1);
    }

}
