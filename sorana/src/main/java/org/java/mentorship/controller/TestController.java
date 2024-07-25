package org.java.mentorship.controller;

import org.java.mentorship.domain.Song;
import org.java.mentorship.service.TestService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    private final TestService testService;

    public TestController(TestService testService,
                          @Qualifier("mapOfProperties") Map<String, String> properties) {
        this.testService = testService;
    }

    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getAllSongs(){
        Song song = new Song();
        song.setId(1);

        List<Song> songs = new ArrayList<>();
        songs.add(song);

        return ResponseEntity.ok(songs);
    }

}
