package org.java.mentorship.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.domain.Album;
import org.java.mentorship.exception.NoEntityFoundException;
import org.java.mentorship.persistence.AlbumRepository;
import org.java.mentorship.validation.AlbumValidator;
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
public class AlbumServiceTest {

    @Mock
    private AlbumRepository repository;

    @Mock
    private AlbumValidator validator;

    private AlbumService albumService;

    @Captor
    private ArgumentCaptor<Album> albumArgumentCaptor;

    @BeforeEach
    void init() {
        albumService = new AlbumService(repository, validator);
    }

    @Test
    void saveShouldValidateAndCallRepository() {
        // Given
        Album album = new Album();
        album.setName("Name");
        album.setArtistId(1);

        when(repository.save(album)).thenAnswer(invocationOnMock -> {
            Album argument = invocationOnMock.getArgument(0, Album.class);
            Album expected = new Album();
            expected.setName(argument.getName());
            expected.setArtistId(argument.getArtistId());
            expected.setId(1);
            return expected;
        });

        // When
        Album result = albumService.save(album);

        // Then
        verify(validator, times(1)).validate(albumArgumentCaptor.capture());
        assertEquals(1, result.getId());

        Album capturedAlbum = albumArgumentCaptor.getValue();
        assertNotNull(capturedAlbum);
        assertEquals(album.getName(), capturedAlbum.getName());
        assertEquals(album.getArtistId(), capturedAlbum.getArtistId());
    }

    @Test
    void findByIdShouldReturnAlbumWhenFound() {
        // Given
        Album album = new Album();
        album.setId(1);
        album.setName("Name");
        album.setArtistId(1);

        when(repository.findById(1)).thenReturn(album);

        // When
        Album result = albumService.findById(1);

        // Then
        assertEquals(album, result);
    }

    @Test
    void findByIdShouldThrowExceptionWhenAlbumNotFound() {
        // Given
        when(repository.findById(1)).thenReturn(null);

        // When & Then
        NoEntityFoundException thrown = assertThrows(NoEntityFoundException.class, () -> albumService.findById(1));
        assertEquals("Album with id 1 not found", thrown.getMessage());
    }

    @Test
    void updateShouldValidateAndCallRepository() {
        // Given
        Album album = new Album();
        album.setId(1);
        album.setName("Updated Name");
        album.setArtistId(1);

        when(repository.findById(1)).thenReturn(album);
        when(repository.update(album)).thenReturn(album);

        // When
        Album result = albumService.update(album);

        // Then
        verify(validator, times(1)).validate(albumArgumentCaptor.capture());
        assertEquals(album, result);

        Album capturedAlbum = albumArgumentCaptor.getValue();
        assertNotNull(capturedAlbum);
        assertEquals(album.getName(), capturedAlbum.getName());
        assertEquals(album.getArtistId(), capturedAlbum.getArtistId());
    }

    @Test
    void updateShouldThrowExceptionWhenAlbumNotFound() {
        // Given
        Album album = new Album();
        album.setId(1);

        when(repository.findById(1)).thenReturn(null);

        // When & Then
        NoEntityFoundException thrown = assertThrows(NoEntityFoundException.class, () -> albumService.update(album));
        assertEquals("Album with id 1 not found", thrown.getMessage());
    }

    @Test
    void deleteShouldCallRepositoryAndReturnDeletedAlbum() {
        // Given
        Album album = new Album();
        album.setId(1);
        album.setName("Name");
        album.setArtistId(1);

        when(repository.findById(1)).thenReturn(album);

        // When
        Album result = albumService.delete(1);

        // Then
        verify(repository, times(1)).delete(1);
        assertEquals(album, result);
    }

    @Test
    void deleteShouldThrowExceptionWhenAlbumNotFound() {
        // Given
        when(repository.findById(1)).thenReturn(null);

        // When & Then
        NoEntityFoundException thrown = assertThrows(NoEntityFoundException.class, () -> albumService.delete(1));
        assertEquals("Album with id 1 not found", thrown.getMessage());
    }

    @Test
    void findAllShouldReturnListOfAlbums() {
        // Given
        Album album1 = new Album();
        album1.setId(1);
        album1.setName("Name1");
        album1.setArtistId(1);

        Album album2 = new Album();
        album2.setId(2);
        album2.setName("Name2");
        album2.setArtistId(2);

        List<Album> albums = Arrays.asList(album1, album2);
        when(repository.findAll()).thenReturn(albums);

        // When
        List<Album> result = albumService.findAll();

        // Then
        assertEquals(albums, result);
    }
}
