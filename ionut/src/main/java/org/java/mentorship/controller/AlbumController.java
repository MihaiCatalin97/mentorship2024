package org.java.mentorship.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.domain.Album;
import org.java.mentorship.service.AlbumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping
    public ResponseEntity<Album> create(@RequestBody final Album album) {
        return ResponseEntity
                .status(CREATED)
                .body(albumService.save(album));
    }

    @GetMapping
    public ResponseEntity<List<Album>> getAll() {
        return ResponseEntity.ok(albumService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getById(@PathVariable("id") final int id) {
        return ResponseEntity.ok(albumService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Album> update(@PathVariable("id") final int id,
                                        @RequestBody final Album album) {
        album.setId(id);
        return ResponseEntity.ok(albumService.update(album));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Album> delete(@PathVariable("id") final int id) {
        return ResponseEntity.ok(albumService.delete(id));
    }
}
