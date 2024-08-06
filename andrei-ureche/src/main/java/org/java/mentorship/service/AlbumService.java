package org.java.mentorship.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.common.EntityService;
import org.java.mentorship.domain.Album;
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
}