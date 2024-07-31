package org.java.mentorship.andrei_s.service;

import lombok.AllArgsConstructor;
import org.java.mentorship.andrei_s.domain.Album;
import org.java.mentorship.andrei_s.exception.EntityNotFound;
import org.java.mentorship.andrei_s.persistence.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AlbumService {
    AlbumRepository repo;

    public Album getById(int id) {
        Album foundAlbum = repo.getById(id);
        if (Objects.isNull(foundAlbum)) throw new EntityNotFound(id);
        return foundAlbum;
    }

    public List<Album> findAll() {

        return repo.findAll();
    }

    public Album createNew(Album album) {
        Album.validate(album);
        return repo.createNew(album);
    }

    public List<Album> getArtistAlbums(int artist_id) {
        return repo.getArtistAlbums(artist_id);
    }

    public void deleteById(int id) {

        repo.deleteById(id);
    }

    public Album updateById(int id, Album modified) {
        Album.validate(modified);
        return repo.updateById(id, modified);
    }
}
