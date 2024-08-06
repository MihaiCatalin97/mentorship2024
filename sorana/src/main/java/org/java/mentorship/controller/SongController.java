package org.java.mentorship.controller;

import lombok.AllArgsConstructor;
import org.java.mentorship.domain.Artist;
import org.java.mentorship.domain.Song;
import org.java.mentorship.service.AlbumService;
import org.java.mentorship.service.ArtistService;
import org.java.mentorship.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final ArtistService artistService;
    private final AlbumService albumService;

    @PostMapping("/songs")
    public ResponseEntity<Song> create(@RequestBody final Song song) {
        return ResponseEntity
                .status(CREATED)
                .body(songService.save(song));
    }

//    @GetMapping("/songs")//works
//    public ResponseEntity<List<Song>> getAll() {
//        return ResponseEntity.ok(songService.findAll());
//    }

    @GetMapping("/songs")//works
    public ResponseEntity<List<Song>> getAllQueryParam(@RequestParam(required = false, name = "style") final String style, @RequestParam(required = false, name = "duration") final Integer duration, @RequestParam(required = false, name = "artistId") final Integer artistId,
                                                       @RequestParam(required = false, name = "albumId") final Integer albumId) {

        Map<String, Object> map = new HashMap<>();
        map.put("style", style);
        map.put("duration", duration);
        map.put("artist_id", artistId);
        map.put("album_id", albumId);
        map.values().removeAll(Collections.singleton(null));

        return ResponseEntity.ok().body(songService.find(map));

    }

    @GetMapping("/songs/{id}/artistandalbum")//works
    public ResponseEntity<Map> getAlbumAndArtist(@PathVariable("id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        Song song = songService.findById(id);
        map.put("artist", artistService.findById(song.getArtistId()));
        map.put("album", albumService.findById(song.getAlbumId()));
        return ResponseEntity.ok(map);
    }

    @GetMapping("/songs/{id}")//works
    public ResponseEntity<Song> getById(@PathVariable("id") final int identifier) {
        return ResponseEntity.ok(songService.findById(identifier));
    }

    @GetMapping("/songs/{id}/artist")//works
    public ResponseEntity<Artist> getArtistBySong(@PathVariable("id") final int identifier) {
        Song song = songService.findById(identifier);
        Artist artist = artistService.findById(song.getArtistId());
        return ResponseEntity.ok(artist);
    }

    @PutMapping("/songs/{id}")//works
    public ResponseEntity<Song> update(@PathVariable("id") final int identifier,
                                       @RequestBody final Song song) {
        song.setId(identifier);

        return ResponseEntity.ok(songService.update(identifier, song));
    }

    @DeleteMapping("/songs/{id}")//works
    public ResponseEntity<String> delete(@PathVariable("id") final int identifier) {
        songService.delete(identifier);
        return ResponseEntity.ok("deleted song");
    }


}
