package org.java.mentorship.andrei_s.service;

import lombok.AllArgsConstructor;
import org.java.mentorship.andrei_s.domain.Song;
import org.java.mentorship.andrei_s.exception.EntityNotFound;
import org.java.mentorship.andrei_s.persistence.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class SongService {
    SongRepository repo;

    public Song getSongById(int songId)
    {
        Song foundSong = repo.getById(songId);
        if (Objects.isNull(foundSong)) throw new EntityNotFound(songId);
        return foundSong;
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

    public Song updateById(int id, Song modifiedSong)
    {
        Song.validate(modifiedSong);
        return repo.updateById(id, modifiedSong);
    }
}
