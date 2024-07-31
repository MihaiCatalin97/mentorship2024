package org.java.mentorship.andrei_s.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.andrei_s.domain.Album;
import org.java.mentorship.andrei_s.domain.Song;
import org.java.mentorship.andrei_s.service.AlbumService;
import org.java.mentorship.andrei_s.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping("/albums")
    public ResponseEntity<List<Album>> getAll()
    {
        return ResponseEntity.ok(albumService.findAll());
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<Album> getById(@PathVariable(name = "id") int id)
    {
        Album song = albumService.getById(id);
        return ResponseEntity.ok(song);
    }

    @PostMapping("/albums")
    public ResponseEntity<Album> createNew(@RequestBody Album album)
    {
        Album newAlbum = albumService.createNew(album);
        return ResponseEntity.ok(newAlbum);
    }

    @DeleteMapping("/albums/{id}") ResponseEntity<String> delete(@PathVariable(name = "id") int id)
    {
        albumService.deleteById(id);
        return ResponseEntity.ok("Deleted album");
    }

    @PutMapping("/albums/{id}") ResponseEntity<Album> modify(@PathVariable(name = "id") int id, @RequestBody Album album)
    {
        return ResponseEntity.ok(albumService.updateById(id, album));
    }
}