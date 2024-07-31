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

    public Song getById(int id) {
        Song foundSong = repo.getById(id);
        if (Objects.isNull(foundSong)) throw new EntityNotFound(id);
        return foundSong;
    }

    public List<Song> findAll(String style) {
        return repo.findAll();
    }

    public Song createNew(Song song) {
        Song.validate(song);
        return repo.createNew(song);
    }

    public void deleteById(int id) {
        repo.deleteById(id);
    }

    public Song updateById(int id, Song modified) {
        Song.validate(modified);
        return repo.updateById(id, modified);
    }

    public List<Song> getArtistSongs(int artist_id) {
        return repo.findArtistSongs(artist_id);
    }
}
