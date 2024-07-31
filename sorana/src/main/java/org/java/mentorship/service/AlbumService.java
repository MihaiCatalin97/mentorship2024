package org.java.mentorship.service;

import lombok.AllArgsConstructor;
import org.java.mentorship.domain.Album;
import org.java.mentorship.persistence.AlbumRepository;
import org.java.mentorship.persistence.ArtistRepository;
import org.java.mentorship.validation.AlbumValidator;
import org.java.mentorship.validation.ArtistValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final AlbumValidator albumValidator;

    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    public Album findById(final Integer id) {
        return albumRepository.findById(id);
    }

    public Album save(Album album) {
        albumValidator.validate(album);
        return albumRepository.save(album);
    }

    public boolean delete(final Integer id) {
        return albumRepository.delete(id);
    }

    public Album update(final Integer id, Album album) {
        albumValidator.validate(album);
        return albumRepository.update(id, album);
    }
}