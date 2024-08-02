package org.java.mentorship.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.common.EntityService;
import org.java.mentorship.domain.Song;
import org.java.mentorship.persistence.SongRepository;
import org.java.mentorship.validation.SongValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService implements EntityService<Song, Integer> {

    private final SongRepository repository;
    private final SongValidator validator;

    @Override
    public Song save(final Song song) {
        validator.validate(song);
        return repository.save(song);
    }

    @Override
    public List<Song> findAll() {
        return repository.findAll();
    }

    @Override
    public Song findById(final Integer id) {
        Song song = repository.findById(id);
        if (song == null) {
            throw new IllegalArgumentException("Song with id " + id + " not found");
        }
        return song;
    }

    @Override
    public Song update(final Song song) {
        validator.validate(song);
        Song existingSong = repository.findById(song.getId());
        if (existingSong == null) {
            throw new IllegalArgumentException("Song with id " + song.getId() + " not found");
        }
        return repository.update(song);
    }

    @Override
    public Song delete(final Integer id) {

        Song song = repository.findById(id);
        if (song == null) {
            throw new IllegalArgumentException("Song with id " + id + " not found");
        }
        repository.delete(id);
        return song;
    }
}
