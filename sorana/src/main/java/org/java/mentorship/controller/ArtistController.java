package org.java.mentorship.controller;

import lombok.AllArgsConstructor;
import org.java.mentorship.domain.Artist;
import org.java.mentorship.domain.Song;
import org.java.mentorship.service.ArtistService;
import org.java.mentorship.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;

@Controller
@AllArgsConstructor
public class ArtistController {
    private final ArtistService artistService;
    private final SongService songService;

    @GetMapping("/artists")//works
    public ResponseEntity<List<Artist>> getAllartists(@RequestParam(required = false, name = "id") final Integer id,@RequestParam(required = false, name = "name") final String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.values().removeAll(Collections.singleton(null));

        return ResponseEntity.ok(artistService.findAll(map));
    }

    @GetMapping("/artists/{id}/songs")//works
    public ResponseEntity<List<Song>> getAllSongs(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(songService.findSongs(id));
    }

    @GetMapping("/artists/{id}")//works
    public ResponseEntity<Artist> getArtistById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(artistService.findById(id));
    }

    @PostMapping("/artists")//works
    public ResponseEntity<Artist> create(@RequestBody final Artist artist) {
        return ResponseEntity
                .status(CREATED)
                .body(artistService.save(artist));
    }

    @PutMapping("/artists/{id}")//works
    public ResponseEntity<Artist> update(@PathVariable("id") final int identifier,
                                       @RequestBody final Artist artist) {
        artist.setId(identifier);

        return ResponseEntity.ok(artistService.update(identifier,artist));
    }

    @DeleteMapping("/artists/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final Integer identifier) {

        List<Song> songs = songService.findSongs(identifier);
        if(!songs.isEmpty()) {
            return ResponseEntity.badRequest().body("We need to delete all songs before deleting the artist");
        }
        artistService.delete(identifier);
        return ResponseEntity.ok("deleted artist");
    }
}
