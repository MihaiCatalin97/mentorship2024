package org.java.mentorship.controller;

import lombok.AllArgsConstructor;
import org.java.mentorship.domain.Artist;
import org.java.mentorship.domain.Song;
import org.java.mentorship.service.ArtistService;
import org.java.mentorship.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Controller
@AllArgsConstructor
public class ArtistController {
    private final ArtistService artistService;
    private final SongService songService;

    @GetMapping("/artists")
    public ResponseEntity<List<Artist>> getAllartists() {
        return ResponseEntity.ok(artistService.findAll());
    }

    @GetMapping("/artists/songs/{id}")
    public ResponseEntity<List<Song>> getAllSongs(@PathVariable Integer id) {
        return ResponseEntity.ok(songService.findSongs(id));
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable int id) {
        return ResponseEntity.ok(artistService.findById(id));
    }

    @PostMapping("/artists")
    public ResponseEntity<Artist> create(@RequestBody final Artist artist) {
        return ResponseEntity
                .status(CREATED)
                .body(artistService.save(artist));
    }

    @PutMapping("/artists/{id}")
    public ResponseEntity<Artist> update(@PathVariable("id") final int identifier,
                                       @RequestBody final Artist artist) {
        artist.setId(identifier);

        return ResponseEntity.ok(artistService.update(identifier,artist));
    }

    @DeleteMapping("/artists/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final int identifier) {
        artistService.delete(identifier);
        return ResponseEntity.ok("deleted artist");
    }
}
