package org.java.mentorship.andrei_s.service;

import lombok.AllArgsConstructor;
import org.java.mentorship.andrei_s.domain.Song;
import org.java.mentorship.andrei_s.persistence.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SongService {
    SongRepository repo;

    public Song getSongById(int songId)
    {
        throw new RuntimeException("come back later ;)");
    }

    public List<Song> findAll(String style)
    {
        return repo.findAll();
    }

    public Song createNew(Song song)
    {
        Song.validate(song);
        return repo.createNew(song);
    }

    public boolean deleteSong(int songId)
    {
        throw new RuntimeException("come back later ;)");
    }

    public Song modifySong(int songId, Song modifiedSong)
    {
        throw new RuntimeException("come back later ;)");
    }
}
