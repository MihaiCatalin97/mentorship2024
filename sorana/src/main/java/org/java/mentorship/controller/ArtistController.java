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

    @GetMapping("/artists")//works
    public ResponseEntity<List<Artist>> getAllartists() {
        return ResponseEntity.ok(artistService.findAll());
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
