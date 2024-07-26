package org.java.mentorship.controller;

import org.java.mentorship.domain.Song;
import org.java.mentorship.service.TestService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    private final TestService testService;

    public TestController(TestService testService,
                          @Qualifier("mapOfProperties") Map<String, String> properties) {
        this.testService = testService;
    }

    // afiseaza toate melodiile
    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getAllSongs(){

        return ResponseEntity.ok(testService.getSongs());

    }

//    //afiseaza melodiile dupa un anumit style
//    @GetMapping("/songs")
//    public ResponseEntity<List<Song>> getSongByStyle(@RequestParam(required = true, value = "sortby") String style){
//           return ResponseEntity.ok(testService.getSongsByStyle(style));
//    }

    //afiseaza melodia cu un id specific
    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable(name = "id") int id){
        return ResponseEntity.ok(testService.getSongById(id));
    }

    //adauga o melodie
    @PostMapping("/songs")
    public ResponseEntity<List<Song>> addSong(@RequestParam(name ="id") int id, @RequestParam(name = "style") String style, @RequestParam(name = "artistId") int artistId, @RequestParam(name = "albumId") int albumId){
        Song song =new Song(id, style, artistId, albumId);
        if(testService.addSong1(song)!=null){
            return  ResponseEntity.ok(testService.getSongs());
        }
        return null;
    }

    //sterge o melodie
//    @DeleteMapping("/songs")
//    public ResponseEntity<List<Song>> deleteSongById(int id){
//
//        return  ResponseEntity.ok(testService.deleteSongById(id));
//    }


}
