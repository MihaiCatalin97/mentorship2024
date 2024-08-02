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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SongServiceTest {

    @Mock
    private SongRepository repository;

    @Mock
    private SongValidator validator;

    private SongService songService;

    @Captor
    private ArgumentCaptor<Song> songArgumentCaptor;

    @BeforeEach
    void init() {
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
        assertEquals(song.getName(), capturedSong.getName());
        assertEquals(song.getStyle(), capturedSong.getStyle());
    }

    @Test
    void findByIdShouldReturnSongWhenFound() {
        // Given
        Song song = new Song();
        song.setId(1);
        song.setName("Name");
        song.setStyle("Style");

        when(repository.findById(1)).thenReturn(song);

        // When
        Song result = songService.findById(1);

        // Then
        assertEquals(song, result);
    }

    @Test
    void findByIdShouldThrowExceptionWhenSongNotFound() {
        // Given
        when(repository.findById(1)).thenReturn(null);

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> songService.findById(1));
        assertEquals("Song with id 1 not found", thrown.getMessage());
    }

    @Test
    void updateShouldValidateAndCallRepository() {
        // Given
        Song song = new Song();
        song.setId(1);
        song.setName("Updated Name");
        song.setStyle("Updated Style");

        when(repository.findById(1)).thenReturn(song);
        when(repository.update(song)).thenReturn(song);

        // When
        Song result = songService.update(song);

        // Then
        verify(validator, times(1)).validate(songArgumentCaptor.capture());
        assertEquals(song, result);

        Song capturedSong = songArgumentCaptor.getValue();
        assertNotNull(capturedSong);
        assertEquals(song.getName(), capturedSong.getName());
        assertEquals(song.getStyle(), capturedSong.getStyle());
    }

    @Test
    void updateShouldThrowExceptionWhenSongNotFound() {
        // Given
        Song song = new Song();
        song.setId(1);

        when(repository.findById(1)).thenReturn(null);

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> songService.update(song));
        assertEquals("Song with id 1 not found", thrown.getMessage());
    }

    @Test
    void deleteShouldCallRepositoryAndReturnDeletedSong() {
        // Given
        Song song = new Song();
        song.setId(1);
        song.setName("Name");
        song.setStyle("Style");

        when(repository.findById(1)).thenReturn(song);

        // When
        Song result = songService.delete(1);

        // Then
        verify(repository, times(1)).delete(1);
        assertEquals(song, result);
    }

    @Test
    void deleteShouldThrowExceptionWhenSongNotFound() {
        // Given
        when(repository.findById(1)).thenReturn(null);

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> songService.delete(1));
        assertEquals("Song with id 1 not found", thrown.getMessage());
    }

    @Test
    void findAllShouldReturnListOfSongs() {
        // Given
        Song song1 = new Song();
        song1.setId(1);
        song1.setName("Name1");
        song1.setStyle("Style1");

        Song song2 = new Song();
        song2.setId(2);
        song2.setName("Name2");
        song2.setStyle("Style2");

        List<Song> songs = Arrays.asList(song1, song2);
        when(repository.findAll()).thenReturn(songs);

        // When
        List<Song> result = songService.findAll();

        // Then
        assertEquals(songs, result);
    }
}
