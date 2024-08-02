package org.java.mentorship.service;

import lombok.AllArgsConstructor;
import org.java.mentorship.domain.Artist;
import org.java.mentorship.exception.EntityNotFound;
import org.java.mentorship.persistence.ArtistRepository;
import org.java.mentorship.validation.ArtistValidator;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistValidator artistValidator;

    public List<Artist> findAll(Map<String,Object> map) {
        List<Artist> artists =  artistRepository.findAll(map);
        return artists;
    }

    public Artist findById(final Integer id) {
        try{
            return artistRepository.findById(id);
        } catch (EmptyResultDataAccessException e){
            throw new EntityNotFound(id,"artist");
        }
    }

    public Artist save(Artist artist) {
        artistValidator.validate(artist);
        return artistRepository.save(artist);
    }

    public boolean delete(final int id) {

        return artistRepository.delete(id);
    }

    public Artist update(final Integer id,Artist artist) {
        artistValidator.validate(artist);
        return artistRepository.update(id,artist);
    }

}
