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
//first try
//    private List<Song> songs;
//
//    public List<Song> getSongs(){
//        return this.songs;
//    }
//
//    public List<Song> getSongsByStyle(String style){
//
//        if(style == null) return this.songs;
//
//        List<Song> songsByStyle = new ArrayList<>();
//
//        songsByStyle.stream()
//                .filter(song -> song.getStyle().equals(style))
//                .collect(Collectors.toList());
//        return songsByStyle;
//    }
//
//    public Song getSongById(int id){
//        if(id == 0) return null;
//        return this.songs.stream()
//                .filter(it-> it.getId() == id)
//                .findFirst().get();
//    }
//
//    public boolean addSong1(Song song){
//
//        int songId = 0;
//        if(!this.songs.isEmpty())
//            songId = this.songs.getLast().getId() + 1;
//
//        if(song.getArtistId() != 0 && song.getStyle() != null && song.getAlbumId()!=0){
//            return this.songs.add(new Song(
//                    songId,
//                    song.getStyle(),
//                    song.getArtistId(),
//                    song.getAlbumId()
//            ));
//        }
//
//
//        return false;
//    }
//
//    public List<Song> deleteSongById(int id){
//        this.songs.remove(this.getSongById(id));
//        return this.songs;
//    }
//
//    public Song updateSongById(int id, Song song){
//        this.songs.get(id).setStyle(song.getStyle());
//        this.songs.get(id).setArtistId(song.getArtistId());
//        this.songs.get(id).setAlbumId(song.getAlbumId());
//        return this.songs.get(id);
//
//    }

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
