package org.java.mentorship.andrei_s.controller;

import org.java.mentorship.andrei_s.domain.Song;
import org.java.mentorship.andrei_s.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    private final TestService testService;

    TestController(TestService testService,
                   Map<String, String> properties)
    {
        this.testService = testService;
    }

    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getAllSongs()
    {
        List<Song> songs = new LinkedList<>();

        Song song = new Song();
        song.setId(1);

        songs.add(song);

        return ResponseEntity.ok(songs);
    }
}
