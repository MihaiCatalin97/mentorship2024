package org.java.mentorship.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.common.EntityService;
import org.java.mentorship.domain.Song;
import org.java.mentorship.persistence.SongRepository;
import org.java.mentorship.validation.SongValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService implements EntityService<Song, Integer> {

    private final SongRepository repository;

    private final SongValidator validator;

    @Override
    @Transactional
    public Song save(final Song song) {
        validator.validate(song);

        return repository.save(song);
    }

    @Override
    public List<Song> findAll() {
        return repository.findAll();
    }
}
