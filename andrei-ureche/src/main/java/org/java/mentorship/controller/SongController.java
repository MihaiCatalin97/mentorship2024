package org.java.mentorship.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.domain.Song;
import org.java.mentorship.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping("/songs")
    public ResponseEntity<Song> create(@RequestBody final Song song) {
        return ResponseEntity
                .status(CREATED)
                .body(songService.save(song));
    }

    //  getById
    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getAll() {
        return ResponseEntity.ok(songService.findAll());
    }

    //  Create
    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getById(@PathVariable("id") final int identifier) {
        return ResponseEntity.ok(songService.findById(identifier));
    }

    // Update
    @PutMapping("/songs/{id}")
    public ResponseEntity<Song> update(@PathVariable("id") final int identifier, @RequestBody final Song song) {
        return ResponseEntity.ok(songService.update(song));
    }

    //  Delete
    @DeleteMapping("/songs/{id}")
    public ResponseEntity<Song> delete(@PathVariable("id") final int identifier) {
        return ResponseEntity.ok(songService.delete(identifier));
    }
}
