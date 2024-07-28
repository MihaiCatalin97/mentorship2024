package org.java.mentorship.andrei_s.service;

import org.java.mentorship.andrei_s.domain.Song;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class SongService {

    private final LinkedList<Song> songs;
    private int currentId = 0;

    SongService()
    {
        this.songs = new LinkedList<>();

        this.createNewSong(new Song(1, "Pop", 10, 10));
        this.createNewSong(new Song(2, "Pop", 10, 10));
        this.createNewSong(new Song(3, "Rock", 10, 11));
        this.createNewSong(new Song(4, "Rock", 10, 11));
        this.createNewSong(new Song(5, "Rock", 10, 12));
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

    public Song createNewSong(Song song)
    {
        if (song.getStyle() == null) return null;

        Song newSong = new Song(
            currentId++,
            song.getStyle(),
            song.getArtistId(),
            song.getAlbumId()
        );
        songs.add(newSong);

        return newSong;
    }

    public boolean deleteSong(int songId)
    {
        return this.songs.remove(this.getSongById(songId));
    }

    public Song modifySong(int songId, Song modifiedSong)
    {
        Song song = this.getSongById(songId);
        if (song == null) return null;

        song.setStyle(modifiedSong.getStyle());
        song.setArtistId(modifiedSong.getArtistId());
        song.setAlbumId(modifiedSong.getAlbumId());

        return song;
    }
}
