package org.java.mentorship.controller;

import lombok.AllArgsConstructor;
import org.java.mentorship.domain.Song;
import org.java.mentorship.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
public class SongController {

//    private final TestService testService;
//
//    public TestController(TestService testService,
//                          @Qualifier("mapOfProperties") Map<String, String> properties) {
//        this.testService = testService;
//    }
//
//    // afiseaza toate melodiile
//    @GetMapping("/songs")
//    public ResponseEntity<List<Song>> getAllSongs(){
//
//        return ResponseEntity.ok(testService.getSongs());
//
//    }

//    //afiseaza melodiile dupa un anumit style
//    @GetMapping("/songs")
//    public ResponseEntity<List<Song>> getSongByStyle(@RequestParam(required = true, value = "sortby") String style){
//           return ResponseEntity.ok(testService.getSongsByStyle(style));
//    }
//
//    //afiseaza melodia cu un id specific
//    @GetMapping("/songs/{id}")
//    public ResponseEntity<Song> getSongById(@PathVariable(name = "id") int id){
//        return ResponseEntity.ok(testService.getSongById(id));
//    }
//
//    //adauga o melodie
//    @PostMapping("/songs")
//    public ResponseEntity<List<Song>> addSong(@RequestParam(name ="id") int id, @RequestParam(name = "style") String style, @RequestParam(name = "artistId") int artistId, @RequestParam(name = "albumId") int albumId){
//        Song song =new Song(id, style, artistId, albumId);
//        testService.addSong1(song);
//       return ResponseEntity.ok(testService.getSongs());
//    }
//
//    //sterge o melodie
//    @DeleteMapping("/songs/{id}")
//    public ResponseEntity<List<Song>> deleteSongById(@PathVariable(name = "id") int id){
//        return  ResponseEntity.ok(testService.deleteSongById(id));
//    }
//
//    //update a song
//    @PutMapping("/songs{id}")
//    public ResponseEntity<Song> updateSong(@PathVariable(name = "id") int id, @RequestBody Song song){
//       Song songMod = testService.updateSongById(id, song);
//       return ResponseEntity.ok(songMod);
//    }
//
    private final SongService songService;

    @PostMapping("/songs")
    public ResponseEntity<Song> create(@RequestBody final Song song) {
        return ResponseEntity
                .status(CREATED)
                .body(songService.save(song));
    }

    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getAll() {
        return ResponseEntity.ok(songService.findAll());
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getById(@PathVariable("id") final int identifier) {
        return ResponseEntity.ok(songService.findById(identifier));
    }

    @PutMapping("/songs/{id}")
    public ResponseEntity<Song> update(@PathVariable("id") final int identifier,
                                       @RequestBody final Song song) {
        song.setId(identifier);

        return ResponseEntity.ok(songService.update(identifier,song));
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final int identifier) {
        songService.delete(identifier);
        return ResponseEntity.ok("deleted song");
    }

}
