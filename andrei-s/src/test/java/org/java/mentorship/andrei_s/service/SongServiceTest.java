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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
        when(songRepository.find("Style", 1, 1)).thenAnswer(invocationOnMock -> {
            String style = invocationOnMock.getArgument(0);
            int artistId = invocationOnMock.getArgument(1);
            int albumId = invocationOnMock.getArgument(2);
            Song song = new Song();
            List<Song> songs = new ArrayList<>();
            song.setId(123);
            song.setStyle(style);
            song.setAlbumId(albumId);
            song.setArtistId(artistId);
            songs.add(song);
            return songs;
        });

        List<Song> result = songService.find("Style", 1,1);

        verify(songRepository, times(1)).find("Style", 1, 1);
        assertThat(result.getFirst())
                .extracting("style", "artistId", "albumId")
                .containsExactly("Style", 1, 1);

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
