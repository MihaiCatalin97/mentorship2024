package org.java.mentorship.andrei_s.service;

import lombok.Data;
import org.java.mentorship.andrei_s.domain.Song;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;

@Service
public class TestService {

    private LinkedList<Song> songs;

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

    public Song getSong(int songId)
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
        if (this.songs.isEmpty()) song.setId(0);
        else song.setId(this.songs.getLast().getId() + 1);
        // 100% sigur există o metodă mai bună =))
        if (song.getAlbumId() != 0 && song.getStyle() != null && song.getArtistId() != 0)
            return songs.add(song);
        else
            return false;
    }
}
