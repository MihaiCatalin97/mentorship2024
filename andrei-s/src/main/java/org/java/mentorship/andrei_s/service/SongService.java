package org.java.mentorship.andrei_s.service;

import lombok.AllArgsConstructor;
import org.java.mentorship.andrei_s.domain.Song;
import org.java.mentorship.andrei_s.exception.EntityNotFound;
import org.java.mentorship.andrei_s.persistence.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class SongService {
    SongRepository repo;

    ArtistService artistService;
    AlbumService albumService;

    public Song getById(int id) {
        try {
            return repo.getById(id);
        } catch (NoSuchElementException e) {
            throw new EntityNotFound(id, "song");
        }
    }

    public List<Song> find(String style, Integer artistId, Integer albumId) {
        return repo.find(style, artistId, albumId);
    }

    public Song createNew(Song song) {
        Song.validate(song);
        return repo.createNew(song);
    }

    public void deleteById(int id) {
        this.getById(id);
        repo.deleteById(id);
    }

    public Song updateById(int id, Song modified) {
        this.getById(id);
        Song.validate(modified);
        return repo.updateById(id, modified);
    }

    public List<Song> getArtistSongs(int artist_id) {
        artistService.getById(artist_id);
        return repo.findByArtist(artist_id);
    }

    public List<Song> getAlbumSongs(int album_id) {
        albumService.getById(album_id);
        return repo.findByAlbum(album_id);
    }
}
