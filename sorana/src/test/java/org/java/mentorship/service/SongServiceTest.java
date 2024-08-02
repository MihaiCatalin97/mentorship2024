package org.java.mentorship.service;


import org.java.mentorship.domain.Artist;
import org.java.mentorship.domain.Song;
import org.java.mentorship.exception.EntityNotFound;
import org.java.mentorship.persistence.ArtistRepository;
import org.java.mentorship.persistence.SongRepository;
import org.java.mentorship.validation.SongValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SongServiceTest {

    private Song song;

    private SongService songService;

    @Mock
    private SongRepository songRepository;

   @Mock
   private ArtistRepository artistRepository;

    @Mock
    private SongValidator validator;

    @Captor
    private ArgumentCaptor<Song> songCaptor;

    @BeforeEach
    void init(){
        songService = new SongService(songRepository, validator);
    }

    @Test
    void saveShouldSaveObjectToDataBase(){
        Song song = new Song();
        song.setName("name");
        song.setStyle("Style");

        when(songRepository.save(song)).thenAnswer(invocation -> {
            Song argument = (Song) invocation.getArguments()[0];
            Song expected = new Song();
            expected.setName("name");
            expected.setStyle("Style");
            expected.setId(1);
            return expected;

        });

        //when
        Song result = songService.save(song);

        //then
        verify(validator).validate(songCaptor.capture());
        assertEquals(1, result.getId());

        Song captured = songCaptor.getValue();
        Assertions.assertNotNull(captured);
    }

    @Test
    void getByIdShouldSearchASongById(){
        int id = 4;

        when(songRepository.findById(4)).thenReturn(new Song(id, "Fei1n","Rap",2,1,1));

        Song foundSong = songService.findById(id);

        assertEquals(4, foundSong.getId());

        verify(songRepository, times(1)).findById(id);
    }

    @Test
    void getByIdShouldThrowExceptionWhenSongNotFound(){
        Integer id = 3;

        when(songRepository.findById(id)).thenThrow(new EmptyResultDataAccessException(3));

        EntityNotFound exception = assertThrows(EntityNotFound.class, () -> {
            songService.findById(id);
        });

        assertEquals(id, exception.getId());
        assertEquals("song", exception.getFieldName());
    }



    @Test
    void findShouldReturnAllSongs(){
        Map<String, Object> requestParam = new HashMap<>();

        when(songRepository.find(requestParam)).thenAnswer(invocationOnMock -> {
            List<Song> songs = List.of(new Song(1,"Fei1n","Rap",2,1,1));
            return songs;
        });

        List<Song> result = songService.find(requestParam);
        verify(songRepository, times(1)).find(requestParam);
        assertEquals(result.size(), 1);

    }

    @Test
    void deleteShouldDeleteObjectFromDataBase(){
        Integer id = 4;

        when(songRepository.delete(id)).thenReturn(true);

       boolean result = songService.delete(id);

        verify(songRepository, times(1)).delete(id);
        assertTrue(result);
    }

    @Test
    void updateShouldUpdateObjectFromDataBase(){
        Integer id = 4;
        Song song = new Song(1,"Fei1n","Rap",2,1,1);
        when(songRepository.update(id,song)).thenAnswer(invocationOnMock -> {
            song.setDuration(3);
            return song;
        });

        Song result = songService.update(id,song);

        verify(validator).validate(songCaptor.capture());

        assertEquals(song,result);

        Song captured = songCaptor.getValue();
        Assertions.assertNotNull(captured);
    }

    @Test
    void findSongsByArtistShouldReturnAllSongs(){
        Integer artistId = 1;

        List<Song> song = List.of(new Song(1,"Fei1n","Rap",2,1,1));

        when(songRepository.findSongs(artistId)).thenReturn(song);

        List<Song> result = songService.findSongs(artistId);

        verify(songRepository, times(1)).findSongs(artistId);
        assertEquals(song, result);

    }

    @Test
    void findSongsByAlbumShouldReturnAllSongs(){
        Integer albumId = 1;

        List<Song> song = List.of(new Song(1,"Fei1n","Rap",2,1,1));

        when(songRepository.findSongsByAlbumId(albumId)).thenReturn(song);

        List<Song> result = songService.findSongsByAlbumId(albumId);

        verify(songRepository, times(1)).findSongsByAlbumId(albumId);
        assertEquals(song, result);

    }

}
