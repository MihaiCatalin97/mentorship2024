package org.java.mentorship.service;

import org.java.mentorship.domain.Album;
import org.java.mentorship.domain.Song;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestService {

    private final List<Song> songs = new ArrayList<>();
    private int currentId = 1;

    public List<Song> getAllSongs() {
        return new ArrayList<>(songs);
    }

    public Optional<Song> getSongById(int id) {
        return songs.stream().filter(song -> song.getId() == id).findFirst();
    }

<<<<<<< HEAD
    public Song createSong(String style, int artistId, Album album) {
        Song song = new Song(currentId++, style, artistId, album);
=======
    public Song createSong(String style, int artistId, int albumId) {
        Song song = new Song(currentId++, style, artistId, albumId);
>>>>>>> 60fbd81ee044da0ffcdae78b021f97199e1cb409
        songs.add(song);
        return song;
    }

    public Optional<Song> updateSong(int id, Song songDetails) {
        Optional<Song> optionalSong = getSongById(id);

        optionalSong.ifPresent(song -> {
            song.setStyle(songDetails.getStyle());
            song.setArtistId(songDetails.getArtistId());
<<<<<<< HEAD
            song.setAlbum(songDetails.getAlbum());
=======
            song.setAlbumId(songDetails.getAlbumId());
>>>>>>> 60fbd81ee044da0ffcdae78b021f97199e1cb409
        });

        return optionalSong;
    }

    public boolean deleteSong(int id) {
        return songs.removeIf(song -> song.getId() == id);
    }
}
