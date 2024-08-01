package org.java.mentorship.andrei_s.service;

import lombok.AllArgsConstructor;
import org.java.mentorship.andrei_s.domain.Artist;
import org.java.mentorship.andrei_s.exception.AppException;
import org.java.mentorship.andrei_s.exception.EntityNotFound;
import org.java.mentorship.andrei_s.persistence.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ArtistService {
    ArtistRepository repo;

    public Artist getById(int id) {
        try {
            return repo.getById(id);
        } catch (NoSuchElementException e) {
            throw new EntityNotFound(id, "artist");
        }
    }

    public List<Artist> findAll() {
        return repo.findAll();
    }

    public Artist createNew(Artist artist) {
        artist.validate();
        return repo.createNew(artist);
    }

    public void deleteById(int id) {
        repo.getById(id);
        try {
            repo.deleteById(id);
        } catch (Exception e) {
            throw new AppException("Unknown error when deleting artist. Does it still own albums?", e);
        }
    }

    public Artist updateById(int id, Artist modified) {
        repo.getById(id);
        modified.validate();
        return repo.updateById(id, modified);
    }
}
