package org.java.mentorship.service;

import org.java.mentorship.domain.Artist;
import org.java.mentorship.exception.NoEntityFoundException;
import org.java.mentorship.persistence.ArtistRepository;
import org.java.mentorship.validation.ArtistValidator;
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
public class ArtistServiceTest {

    @Mock
    private ArtistRepository repository;

    @Mock
    private ArtistValidator validator;

    private ArtistService artistService;

    @Captor
    private ArgumentCaptor<Artist> artistArgumentCaptor;

    @BeforeEach
    void init() {
        artistService = new ArtistService(repository, validator);
    }

    @Test
    void saveShouldValidateAndCallRepository() {
        // Given
        Artist artist = new Artist();
        artist.setName("Artist Name");

        when(repository.save(artist)).thenAnswer(invocationOnMock -> {
            Artist argument = invocationOnMock.getArgument(0, Artist.class);
            Artist expected = new Artist();
            expected.setName(argument.getName());
            expected.setId(1);
            return expected;
        });

        // When
        Artist result = artistService.save(artist);

        // Then
        verify(validator, times(1)).validate(artistArgumentCaptor.capture());
        assertEquals(1, result.getId());

        Artist capturedArtist = artistArgumentCaptor.getValue();
        assertNotNull(capturedArtist);
        assertEquals(artist.getName(), capturedArtist.getName());
    }

    @Test
    void findByIdShouldReturnArtistWhenFound() {
        // Given
        Artist artist = new Artist();
        artist.setId(1);
        artist.setName("Artist Name");

        when(repository.findById(1)).thenReturn(artist);

        // When
        Artist result = artistService.findById(1);

        // Then
        assertEquals(artist, result);
    }

    @Test
    void findByIdShouldThrowExceptionWhenArtistNotFound() {
        // Given
        when(repository.findById(1)).thenReturn(null);

        // When & Then
        NoEntityFoundException thrown = assertThrows(NoEntityFoundException.class, () -> artistService.findById(1));
        assertEquals("Artist with id 1 not found", thrown.getMessage());
    }

    @Test
    void updateShouldValidateAndCallRepository() {
        // Given
        Artist artist = new Artist();
        artist.setId(1);
        artist.setName("Updated Artist Name");

        when(repository.findById(1)).thenReturn(artist);
        when(repository.update(artist)).thenReturn(artist);

        // When
        Artist result = artistService.update(artist);

        // Then
        verify(validator, times(1)).validate(artistArgumentCaptor.capture());
        assertEquals(artist, result);

        Artist capturedArtist = artistArgumentCaptor.getValue();
        assertNotNull(capturedArtist);
        assertEquals(artist.getName(), capturedArtist.getName());
    }

    @Test
    void updateShouldThrowExceptionWhenArtistNotFound() {
        // Given
        Artist artist = new Artist();
        artist.setId(1);

        when(repository.findById(1)).thenReturn(null);

        // When & Then
        NoEntityFoundException thrown = assertThrows(NoEntityFoundException.class, () -> artistService.update(artist));
        assertEquals("Artist with id 1 not found", thrown.getMessage());
    }

    @Test
    void deleteShouldCallRepositoryAndReturnDeletedArtist() {
        // Given
        Artist artist = new Artist();
        artist.setId(1);
        artist.setName("Artist Name");

        when(repository.findById(1)).thenReturn(artist);

        // When
        Artist result = artistService.delete(1);

        // Then
        verify(repository, times(1)).delete(1);
        assertEquals(artist, result);
    }

    @Test
    void deleteShouldThrowExceptionWhenArtistNotFound() {
        // Given
        when(repository.findById(1)).thenReturn(null);

        // When & Then
        NoEntityFoundException thrown = assertThrows(NoEntityFoundException.class, () -> artistService.delete(1));
        assertEquals("Artist with id 1 not found", thrown.getMessage());
    }

    @Test
    void findAllShouldReturnListOfArtists() {
        // Given
        Artist artist1 = new Artist();
        artist1.setId(1);
        artist1.setName("Artist 1");

        Artist artist2 = new Artist();
        artist2.setId(2);
        artist2.setName("Artist 2");

        List<Artist> artists = Arrays.asList(artist1, artist2);
        when(repository.findAll()).thenReturn(artists);

        // When
        List<Artist> result = artistService.findAll();

        // Then
        assertEquals(artists, result);
    }
}
