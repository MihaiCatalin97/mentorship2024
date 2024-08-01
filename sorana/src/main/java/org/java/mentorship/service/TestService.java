package org.java.mentorship.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.java.mentorship.domain.Song;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class TestService {

    private List<Song> songs;

    public List<Song> getSongs(){
        return this.songs;
    }

    public List<Song> getSongsByStyle(String style){

        if(style == null) return this.songs;

        List<Song> songsByStyle = new ArrayList<>();

        songsByStyle.stream()
                .filter(song -> song.getStyle().equals(style))
                .collect(Collectors.toList());
        return songsByStyle;
    }

    public Song getSongById(int id){
        if(id == 0) return null;
        return this.songs.stream()
                .filter(it-> it.getId() == id)
                .findFirst().get();
    }

    public boolean addSong1(Song song){

        int songId = 0;
        if(!this.songs.isEmpty())
            songId = this.songs.getLast().getId() + 1;

        if(song.getArtistId() != 0 && song.getStyle() != null && song.getAlbumId()!=0){
            return this.songs.add(new Song(
                    songId,
                    song.getStyle(),
                    song.getArtistId(),
                    song.getAlbumId()
            ));
        }


        return false;
    }

    public List<Song> deleteSongById(int id){
        this.songs.remove(this.getSongById(id));
        return this.songs;
    }

    public Song updateSongById(int id, Song song){
        this.songs.get(id).setStyle(song.getStyle());
        this.songs.get(id).setArtistId(song.getArtistId());
        this.songs.get(id).setAlbumId(song.getAlbumId());
        return this.songs.get(id);

    }

}
