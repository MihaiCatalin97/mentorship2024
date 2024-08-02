package org.java.mentorship.service;

import org.checkerframework.checker.units.qual.A;
import org.java.mentorship.domain.Album;
import org.java.mentorship.domain.Artist;
import org.java.mentorship.domain.Song;
import org.java.mentorship.exception.EntityNotFound;
import org.java.mentorship.persistence.AlbumRepository;
import org.java.mentorship.persistence.ArtistRepository;
import org.java.mentorship.validation.AlbumValidator;
import org.java.mentorship.validation.ArtistValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTest {
    private Album album;

    private AlbumService albumService;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private AlbumValidator validator;

    @Captor
    private ArgumentCaptor<Album> albumCaptor;

    @BeforeEach
    void init() {
        albumService = new AlbumService(albumRepository, validator);
    }

    @Test
    void findAllShouldReturnAlbums() {
        Map<String, Object> filters = new HashMap<>();
        when(albumRepository.find(filters)).thenAnswer(invocationOnMock -> {
            List<Album> result = new ArrayList<>();
            Album album1 = new Album();
            album1.setId(1);
            Album album2 = new Album();
            album2.setId(2);
            Album album3 = new Album();
            album3.setId(3);
            result.add(album1);
            result.add(album2);
            result.add(album3);
            return result;
        });

        List<Album> expectedAlbum = albumService.findAll(filters);
        assertEquals(expectedAlbum.size(), 3);
    }

    @Test
    void findByIdShouldReturnArtist() {
        Integer id = 1;
        when(albumRepository.findById(id)).thenReturn(album);
        Album result = albumService.findById(id);
        assertEquals(album,result);
    }
    @Test
    void findByIdShouldRThrowAnExceptionWhenArtistNotFound() {
        Integer id = 1;

        when(albumRepository.findById(id)).thenThrow(new EmptyResultDataAccessException(id));

        EntityNotFound e = assertThrows(EntityNotFound.class, () -> albumService.findById(id));
        assertEquals(id,e.getId());
        assertEquals("album",e.getFieldName());
    }

    @Test
    void saveShouldSaveArtist() {
        Album album = new Album();

        when(albumRepository.save(album)).thenAnswer(invocationOnMock -> {
            album.setId(1);
            album.setName("as");
            return album;
        });

        Album result = albumService.save(album);
        assertEquals(album,result);
    }

    @Test
    void deleteShouldDeleteArtist() {
        Integer id = 1;
        when(albumRepository.delete(id)).thenReturn(true);

        boolean result = albumService.delete(id);
        verify(albumRepository).delete(id);
        assertTrue(result);

    }

    @Test
    void updateShouldUpdateAlbum() {
        Integer id = 1;

        Album album1 = new Album();

        when(albumRepository.update(id,album1)).thenAnswer(invocationOnMock -> {
            album1.setId(id);
            album1.setName("as");
            return album1;
        });

        Album result = albumService.update(id, album1);

        verify(validator).validate(albumCaptor.capture());

        assertEquals(album1,result);
        Album capturedAlbum = albumCaptor.getValue();
        assertNotNull(capturedAlbum);

    }

}