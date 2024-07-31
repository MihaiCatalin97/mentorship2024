package org.java.mentorship.andrei_s.service;

import lombok.AllArgsConstructor;
import org.java.mentorship.andrei_s.domain.Artist;
import org.java.mentorship.andrei_s.exception.EntityNotFound;
import org.java.mentorship.andrei_s.persistence.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ArtistService {
    ArtistRepository repo;

    public Artist getById(int id) {
        Artist foundArtist = repo.getById(id);
        if (Objects.isNull(foundArtist)) throw new EntityNotFound(id);
        return foundArtist;
    }

    public List<Artist> findAll() {
        return repo.findAll();
    }

    public Artist createNew(Artist artist) {
        Artist.validate(artist);
        return repo.createNew(artist);
    }

    public void deleteById(int id) {
        repo.deleteById(id);
    }

    public Artist updateById(int id, Artist modified) {
        Artist.validate(modified);
        return repo.updateById(id, modified);
    }
}
