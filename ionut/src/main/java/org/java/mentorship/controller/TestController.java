package org.java.mentorship.controller;

import org.java.mentorship.domain.Album;
import org.java.mentorship.domain.Song;
import org.java.mentorship.service.TestService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TestController {

    private final TestService testService;

    public TestController(TestService testService,
                          @Qualifier("mapOfProperties") Map<String, String> properties) {
        this.testService = testService;
    }

    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songs = testService.getAllSongs();
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/songs/{id}")
<<<<<<< HEAD
    public ResponseEntity<Song> getSongById(@PathVariable int id) {
=======
    public ResponseEntity<Song> getSongById(@PathVariable(name ="id") int id) {
>>>>>>> 60fbd81ee044da0ffcdae78b021f97199e1cb409
        Optional<Song> song = testService.getSongById(id);
        return song.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/songs")
    public ResponseEntity<Song> createSong(@RequestBody Song song) {
        String style = song.getStyle();
        int artistId = song.getArtistId();
<<<<<<< HEAD
        Album album = song.getAlbum();
=======
        int album = song.getAlbumId();
>>>>>>> 60fbd81ee044da0ffcdae78b021f97199e1cb409

        Song createdSong = testService.createSong(style, artistId, album);

        return ResponseEntity.ok(createdSong);
    }


    @PutMapping("/songs/{id}")
<<<<<<< HEAD
    public ResponseEntity<Song> updateSong(@PathVariable int id, @RequestBody Song songDetails) {
=======
    public ResponseEntity<Song> updateSong(@PathVariable(name ="id") int id, @RequestBody Song songDetails) {
>>>>>>> 60fbd81ee044da0ffcdae78b021f97199e1cb409
        Optional<Song> updatedSong = testService.updateSong(id, songDetails);
        return updatedSong.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/songs/{id}")
<<<<<<< HEAD
    public ResponseEntity<Void> deleteSong(@PathVariable int id) {
=======
    public ResponseEntity<Void> deleteSong(@PathVariable(name= "id") int id) {
>>>>>>> 60fbd81ee044da0ffcdae78b021f97199e1cb409
        boolean isDeleted = testService.deleteSong(id);
        return isDeleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}