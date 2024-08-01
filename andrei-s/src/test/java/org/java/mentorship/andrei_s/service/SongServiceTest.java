package org.java.mentorship.andrei_s.service;

import org.java.mentorship.andrei_s.domain.Song;
import org.java.mentorship.andrei_s.exception.EntityNotFound;
import org.java.mentorship.andrei_s.persistence.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SongServiceTest {

    SongService songService;

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
            int id = invocationOnMock.getArgument(0);
            throw new EntityNotFound(id, "song");
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

}
