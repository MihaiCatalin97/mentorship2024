package org.java.mentorship.andrei_s.controller;

import org.java.mentorship.andrei_s.domain.Song;
import org.java.mentorship.andrei_s.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        return ResponseEntity.ok(testService.getSongs());
    }

    @PostMapping("/songs")
    public ResponseEntity<String> createNewSong(@RequestBody Song song)
    {
        if (testService.createNewSong(song))
            return ResponseEntity.ok("Created new song");
        else
            return ResponseEntity.badRequest().body("Missing values");
    }
}
