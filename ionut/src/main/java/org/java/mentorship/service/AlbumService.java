package org.java.mentorship.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.common.EntityService;
import org.java.mentorship.domain.Album;
import org.java.mentorship.exception.NoEntityFoundException;
import org.java.mentorship.persistence.AlbumRepository;
import org.java.mentorship.validation.AlbumValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService implements EntityService<Album, Integer> {

    private final AlbumRepository repository;
    private final AlbumValidator validator;

    @Override
    public Album save(final Album album) {
        validator.validate(album);
        return repository.save(album);
    }

    @Override
    public List<Album> findAll() {
        return repository.findAll();
    }

    @Override
    public Album findById(final Integer id) {
        Album album = repository.findById(id);
        if (album == null) {
            throw new NoEntityFoundException("Album with id " + id + " not found");
        }
        return album;
    }

    @Override
    public Album update(final Album album) {
        validator.validate(album);
        Album existingAlbum = repository.findById(album.getId());
        if (existingAlbum == null) {
            throw new NoEntityFoundException("Album with id " + album.getId() + " not found");
        }
        return repository.update(album);
    }

    @Override
    public Album delete(final Integer id) {
        Album album = repository.findById(id);
        if (album == null) {
            throw new NoEntityFoundException("Album with id " + id + " not found");
        }
        repository.delete(id);
        return album;
    }
}
