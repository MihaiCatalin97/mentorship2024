package org.java.mentorship.andrei_s.service;

import org.java.mentorship.andrei_s.domain.Song;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class TestService {

    private final LinkedList<Song> songs;

    TestService()
    {
        this.songs = new LinkedList<>();

        // adaug niste melodii de test
        this.songs.add(new Song(1, "Pop", 10, 10));
        this.songs.add(new Song(2, "Pop", 10, 10));
        this.songs.add(new Song(3, "Rock", 10, 11));
        this.songs.add(new Song(4, "Rock", 10, 11));
        this.songs.add(new Song(4, "Rock", 10, 12));
    }

    public Song getSongById(int songId)
    {
        for (Song song : songs)
            if (song.getId() == songId) return song;
        return null;
    }

    public LinkedList<Song> getSongs(String style)
    {
        if (style == null) return this.songs;

        LinkedList<Song> filteredSongs = new LinkedList<>();
        for (Song song : this.songs)
            if (song.getStyle().equals(style)) filteredSongs.add(song);

        return filteredSongs;
    }

    public boolean createNewSong(Song song)
    {
        int songId = 0;
        if (!this.songs.isEmpty()) songId = this.songs.getLast().getId() + 1;
        // 100% sigur există o metodă mai bună =))
        if (song.getAlbumId() == 0 || song.getStyle() == null || song.getArtistId() == 0)
            // fac asta aici ca sa ma asigur ca songId este mereu id-ul ultimului element + 1
            // si sa nu poti seta ce ID vrei
            return false;

        return songs.add(new Song(
            songId,
            song.getStyle(),
            song.getArtistId(),
            song.getAlbumId())
        );
    }

    public boolean deleteSong(int songId)
    {
        for (Song song : this.songs)
        {
            if (song.getId() == songId)
            { songs.remove(song); return true; }
        }
        return false;
    }

    public Song modifySong(int songId, Song modified_song)
    {
        Song song = this.getSongById(songId);
        if (song == null) return null;
        song.setStyle(modified_song.getStyle());
        song.setArtistId(modified_song.getArtistId());
        song.setAlbumId(modified_song.getAlbumId());
        return song;
    }
}
