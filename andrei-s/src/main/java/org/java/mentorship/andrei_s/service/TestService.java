package org.java.mentorship.andrei_s.service;

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
    }

    public Song getSong(int songId)
    {
        for (Song song : songs)
            if (song.getId() == songId) return song;
        return null;
    }

    public LinkedList<Song> getSongs()
    { return this.songs; }
}
