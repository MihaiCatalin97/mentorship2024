package org.java.mentorship.service;

import lombok.AllArgsConstructor;
import org.java.mentorship.common.EntityService;
import org.java.mentorship.domain.Song;
import org.java.mentorship.exception.EntityNotFound;
import org.java.mentorship.persistence.SongRepository;
import org.java.mentorship.validation.SongValidator;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@AllArgsConstructor
public class SongService implements EntityService<Song, Integer> {

    private final SongRepository repository;

    private final SongValidator validator;


    @Override
    public Song save(final Song song) {
        validator.validate(song);
        return repository.save(song);
    }

    @Override
    public Song findById(final Integer id) {
        try{
            return repository.findById(id);
        } catch (EmptyResultDataAccessException e){
            throw new EntityNotFound(id,"song");
        }
    }
    @Override
    public List<Song> find(Map<String, Object> requestParam) {
        List<Song> songs = repository.find(requestParam);
        return songs;
    }

    @Override
    public Song update(final Integer id, final Song song){
        validator.validate(song);
        return repository.update(id,song);
    }
    @Override
    public boolean delete(final Integer id) {
        return repository.delete(id);
    }

    public List<Song> findSongs(Integer artistId){
        return repository.findSongs(artistId);
    }

    public List<Song> findSongsByAlbumId(Integer albumId){
        return repository.findSongsByAlbumId(albumId);
    }

}
