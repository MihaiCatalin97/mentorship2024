package org.java.mentorship.andrei_s.service;

import lombok.AllArgsConstructor;
import org.java.mentorship.andrei_s.domain.Album;
import org.java.mentorship.andrei_s.exception.EntityNotFound;
import org.java.mentorship.andrei_s.persistence.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AlbumService {
    AlbumRepository repo;

    ArtistService artistService;

    public Album getById(int id) {
        try {
            return repo.getById(id);
        } catch (NoSuchElementException e) {
            throw new EntityNotFound(id);
        }
    }

    public List<Album> findAll() {
        return repo.findAll();
    }

    public Album createNew(Album album) {
        Album.validate(album);
        return repo.createNew(album);
    }

    public List<Album> getArtistAlbums(int artist_id) {
        artistService.getById(artist_id);
        return repo.getArtistAlbums(artist_id);
    }

    public void deleteById(int id) {
        repo.getById(id);
        repo.deleteById(id);
    }

    public Album updateById(int id, Album modified) {
        repo.getById(id);
        Album.validate(modified);
        return repo.updateById(id, modified);
    }
}
