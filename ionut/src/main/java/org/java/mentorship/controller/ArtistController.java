package org.java.mentorship.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.domain.Artist;
import org.java.mentorship.service.ArtistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping
    public ResponseEntity<Artist> create(@RequestBody final Artist artist) {
        return ResponseEntity
                .status(CREATED)
                .body(artistService.save(artist));
    }

    @GetMapping
    public ResponseEntity<List<Artist>> getAll() {
        return ResponseEntity.ok(artistService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getById(@PathVariable("id") final int id) {
        return ResponseEntity.ok(artistService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> update(@PathVariable("id") final int id,
                                         @RequestBody final Artist artist) {
        artist.setId(id);
        return ResponseEntity.ok(artistService.update(artist));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Artist> delete(@PathVariable("id") final int id) {
        return ResponseEntity.ok(artistService.delete(id));
    }
}
