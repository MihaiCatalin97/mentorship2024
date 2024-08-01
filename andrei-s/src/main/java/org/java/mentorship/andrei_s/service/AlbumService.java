package org.java.mentorship.andrei_s.service;

import lombok.AllArgsConstructor;
import org.java.mentorship.andrei_s.domain.Album;
import org.java.mentorship.andrei_s.exception.AppException;
import org.java.mentorship.andrei_s.exception.EntityNotFound;
import org.java.mentorship.andrei_s.persistence.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class AlbumService {
    AlbumRepository repo;

    public Album getById(int id) {
        try {
            return repo.getById(id);
        } catch (NoSuchElementException e) {
            throw new EntityNotFound(id, "album");
        }
    }

    public List<Album> find() {
        return repo.find();
    }

    public Album createNew(Album album) {
        album.validate();
        return repo.createNew(album);
    }

    public void deleteById(int id) {
        this.getById(id);
        try {
            repo.deleteById(id);
        } catch (Exception e) {
            throw new AppException("Unknown error when deleting album. Does it still contain songs?", e);
        }
    }

    public Album updateById(int id, Album modified) {
        repo.getById(id);
        modified.validate();
        return repo.updateById(id, modified);
    }
}
