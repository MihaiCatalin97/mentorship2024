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

    public Song createSong(String style, int artistId, int albumId) {
        Song song = new Song(currentId++, style, artistId, albumId);
        songs.add(song);
        return song;
    }

    public Optional<Song> updateSong(int id, Song songDetails) {
        Optional<Song> optionalSong = getSongById(id);

        optionalSong.ifPresent(song -> {
            song.setStyle(songDetails.getStyle());
            song.setArtistId(songDetails.getArtistId());
            song.setAlbumId(songDetails.getAlbumId());
        });

        return optionalSong;
    }

    public boolean deleteSong(int id) {
        return songs.removeIf(song -> song.getId() == id);
    }
}
