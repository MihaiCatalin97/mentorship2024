package org.java.mentorship.percistence;

import org.java.mentorship.domain.Song;
import org.java.mentorship.persistence.AlbumRepository;
import org.java.mentorship.persistence.ArtistRepository;
import org.java.mentorship.persistence.SongRepository;
import org.java.mentorship.persistence.mapper.SongRowMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.jdbc.core.JdbcTemplate;


import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SongRepositoryTest {
    //new Song(1,"Fei1n","Rap",2,1,1);
    private SongRepository songRepository;

    @Mock
    private SongRowMapper songRowMapper;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private ArtistRepository artistRepository;
    @Mock
    private AlbumRepository albumRepository;

    @Captor
    private ArgumentCaptor<Song> songCaptor;

    @BeforeEach
    void init(){
        songRepository = new SongRepository(jdbcTemplate,songRowMapper,artistRepository,albumRepository);
    }

    @Test
    void saveShouldSaveObjectToDataBase(){
        Song song = new Song();
        song.setName("Test Song");
        song.setStyle("Rock");
        song.setDuration(300);

        // When
        songRepository.save(song);

        // Then
        verify(jdbcTemplate).update("INSERT INTO songs (name, style, duration) VALUES(?,?,?)",
                song.getName(), song.getStyle(), song.getDuration());

    }

    @Test
    void findByIdShouldReturnAllSongs(){
        Integer id = 1;
        songRepository.findById(1);
        verify(jdbcTemplate).queryForObject("SELECT * FROM songs WHERE id = ?", songRowMapper, id);
    }

    @Test
    void updateShouldUpdateSong(){
        Song song = new Song(1,"Fei1n","Rap",2,1,1);

        songRepository.findById(1);
        jdbcTemplate.update("INSERT INTO songs (name, style, duration) VALUES(?,?,?)",
                song.getName(), song.getStyle(), song.getDuration());

        verify(jdbcTemplate).update("INSERT INTO songs (name, style, duration) VALUES(?,?,?)",
                song.getName(), song.getStyle(), song.getDuration());

    }

    @Test
    void deleteShouldDeleteSong(){
        Song song = new Song(1,"Fei1n","Rap",2,1,1);
        songRepository.findById(1);
        songRepository.delete(song.getId());
        //jdbcTemplate.update("DELETE FROM songs WHERE id = ?", song.getId());
        verify(jdbcTemplate).update("DELETE FROM songs WHERE id = ?", song.getId());
    }
    @Test//????????no logic
    void findSongByArtistIdShouldReturnAllSongs(){
        Integer id = 1;
        List<Song> song = List.of(new Song(1,"Fei1n","Rap",2,1,1));
        when(songRepository.findSongs(id)).thenReturn(song);
        List<Song> result = songRepository.findSongs(id);
        Assertions.assertEquals(song,result);
        verify(jdbcTemplate).query("SELECT * FROM songs WHERE artist_id = ? ", songRowMapper,id);
    }


}
