package org.java.mentorship.service;

import org.java.mentorship.domain.Song;
import org.java.mentorship.persistence.SongRepository;
import org.java.mentorship.validation.SongValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SongServiceTest {

    private SongRepository repository;

    @Mock
    private SongValidator validator;

    private SongService songService;

    @Captor
    private ArgumentCaptor<Song> songArgumentCaptor;

    @BeforeEach
    void init(){
        repository = mock(SongRepository.class);

        songService = new SongService(repository, validator);
    }

    @Test
    void saveShouldValidateAndCallRepository() {
        // Given
        Song song = new Song();
        song.setName("Name");
        song.setStyle("Style");

        when(repository.save(song)).thenAnswer(invocationOnMock -> {
            Song argument = invocationOnMock.getArgument(0, Song.class);

            Song expected = new Song();
            expected.setName(argument.getName());
            expected.setStyle(argument.getStyle());
            expected.setId(1);
            return expected;
        });

        // When
        Song result = songService.save(song);

        // Then
        verify(validator, times(1)).validate(songArgumentCaptor.capture());
        assertEquals(1, result.getId());

        Song capturedSong = songArgumentCaptor.getValue();
        assertNotNull(capturedSong);
    }
}
