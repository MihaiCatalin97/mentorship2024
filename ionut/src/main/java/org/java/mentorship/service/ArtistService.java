package org.java.mentorship.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.common.EntityService;
import org.java.mentorship.domain.Artist;
import org.java.mentorship.exception.NoEntityFoundException;
import org.java.mentorship.persistence.ArtistRepository;
import org.java.mentorship.validation.ArtistValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService implements EntityService<Artist, Integer> {

    private final ArtistRepository repository;
    private final ArtistValidator validator;

    @Override
    public Artist save(final Artist artist) {
        validator.validate(artist);
        return repository.save(artist);
    }

    @Override
    public List<Artist> findAll() {
        return repository.findAll();
    }

    @Override
    public Artist findById(final Integer id) {
        Artist artist = repository.findById(id);
        if (artist == null) {
            throw new NoEntityFoundException("Artist with id " + id + " not found");
        }
        return artist;
    }

    @Override
    public Artist update(final Artist artist) {
        validator.validate(artist);
        Artist existingArtist = repository.findById(artist.getId());
        if (existingArtist == null) {
            throw new NoEntityFoundException("Artist with id " + artist.getId() + " not found");
        }
        return repository.update(artist);
    }

    @Override
    public Artist delete(final Integer id) {
        Artist artist = repository.findById(id);
        if (artist == null) {
            throw new NoEntityFoundException("Artist with id " + id + " not found");
        }
        repository.delete(id);
        return artist;
    }
}
