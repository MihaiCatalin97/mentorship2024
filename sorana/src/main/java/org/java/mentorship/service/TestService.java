package org.java.mentorship.service;

import org.java.mentorship.domain.Song;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TestService {

    List<Song> songs;

    TestService(){
        this.songs = new ArrayList<>();

        this.songs.add(new Song(1, "Pop", 10, 10));
        this.songs.add(new Song(2, "Pop", 10, 10));
        this.songs.add(new Song(3, "Rock", 10, 11));
        this.songs.add(new Song(4, "Rock", 10, 11));
        this.songs.add(new Song(4, "Rock", 10, 12));
    }


    public List<Song> getSongs(){
        return this.songs;
    }

    public List<Song> getSongsByStyle(String style){

        if(style == null) return this.songs;

        List<Song> songsByStyle = new ArrayList<>();

        for(Song song: this.songs){
            if(song.getStyle().equals(style)){
                songsByStyle.add(song);
            }
        }
        return songsByStyle;
    }

    public Song getSongById(int id){
        if(id == 0) return null;
        for(Song song: this.songs){
            if(song.getId() == id){
                return song;
            }
        }
        return null;
    }

    public boolean addSong1(Song song){

        if(this.songs.isEmpty())
            song.setId(1);
        else song.setArtistId(this.songs.getLast().getId()+1);

        if(song.getArtistId() != 0 && song.getStyle() !=null && song.getAlbumId()!=0){
            this.songs.add(song);
            return true;
        }
        else return false;
    }
}
