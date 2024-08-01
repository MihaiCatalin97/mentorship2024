package org.java.mentorship.service;

import org.java.mentorship.domain.Artist;
import org.java.mentorship.domain.Song;
import org.java.mentorship.exception.EntityNotFound;
import org.java.mentorship.persistence.ArtistRepository;

import org.java.mentorship.validation.ArtistValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {
    private Artist artist;

    private ArtistService artistService;

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private ArtistValidator validator;

    @Captor
    private ArgumentCaptor<Artist> artistCaptor;

    @BeforeEach
    void init() {
        artistService = new ArtistService(artistRepository, validator);
    }

    @Test
    void findAllShouldReturnArtists() {
        List<Artist> artists = List.of(new Artist(1, "as"), new Artist(2, "ghj"));

        when(artistRepository.findAll()).thenReturn(artists);
        List<Artist> result = artistService.findAll();
        assertEquals(artists,result);
    }

    @Test
    void findByIdShouldReturnArtist() {
        Integer id = 1;
        when(artistRepository.findById(id)).thenReturn(artist);
        Artist result = artistService.findById(id);
        assertEquals(artist,result);
    }
    @Test
    void findByIdShouldRThrowAnExceptionWhenArtistNotFound() {
        Integer id = 1;

        when(artistRepository.findById(id)).thenThrow(new EmptyResultDataAccessException(id));

        EntityNotFound e = assertThrows(EntityNotFound.class, () -> artistService.findById(id));
        assertEquals(id,e.getId());
        assertEquals("artist",e.getFieldName());
    }

    @Test
    void saveShouldSaveArtist() {
        Artist artist = new Artist();
        when(artistRepository.save(artist)).thenAnswer(invocationOnMock -> {
            artist.setId(1);
            artist.setName("as");
            return artist;
        });

        Artist result = artistService.save(artist);
        assertEquals(artist,result);
    }

    @Test
    void deleteShouldDeleteArtist() {
        Integer id = 1;
        when(artistRepository.delete(id)).thenReturn(true);

        boolean result = artistService.delete(id);
        verify(artistRepository).delete(id);
        assertTrue(result);

    }

    @Test
    void updateShouldUpdateArtist() {
        Integer id = 1;

        Artist artist = new Artist();

        when(artistRepository.update(id,artist)).thenAnswer(invocationOnMock -> {
            artist.setId(id);
            artist.setName("as");
            return artist;

        });

        Artist result = artistService.update(id, artist);
        verify(validator).validate(artistCaptor.capture());

        assertEquals(artist,result);

        Artist capturedArtist = artistCaptor.getValue();
        assertNotNull(capturedArtist);

    }

}
