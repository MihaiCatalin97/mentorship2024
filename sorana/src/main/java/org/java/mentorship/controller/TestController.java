package org.java.mentorship.controller;

import org.java.mentorship.domain.Song;
import org.java.mentorship.service.TestService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        return ResponseEntity.ok(testService.getSongs());

    }


    @GetMapping("/songs/{style}")
    public ResponseEntity<List<Song>> getSongByStyle(@PathVariable("style") String style){
           return ResponseEntity.ok(testService.getSongsByStyle(style));
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable("id") int id){
        return ResponseEntity.ok(testService.getSongById(id));
    }


    @PostMapping("/songs")
    public ResponseEntity<String> addSong(@RequestBody Song song){
        if(testService.addSong1(song)){
            return  ResponseEntity.ok("song added");
        }
        else {
            return  ResponseEntity.badRequest().body("song not added");
        }
    }

}
