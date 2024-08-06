package org.java.mentorship.andrei_s.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.andrei_s.domain.Album;
import org.java.mentorship.andrei_s.service.AlbumService;
import org.java.mentorship.andrei_s.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService albumService;
    private final SongService songService;

    @GetMapping()
    public ResponseEntity<List<Album>> getAll(@RequestParam(name = "artist", required = false) Integer artistId) {
        Map<String, Object> filtersMap = new HashMap<>();
        filtersMap.put("artist_id", artistId);

        return ResponseEntity.ok(albumService.find(filtersMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getById(@PathVariable(name = "id") int id) {
        Album album = albumService.getById(id);
        return ResponseEntity.ok(album);
    }

    @PostMapping()
    public ResponseEntity<Album> createNew(@RequestBody Album album) {
        Album newAlbum = albumService.createNew(album);
        return ResponseEntity.ok(newAlbum);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable(name = "id") int id) {
        albumService.deleteById(id);
        return ResponseEntity.ok("Deleted album");
    }

    @PutMapping("/{id}")
    ResponseEntity<Album> modify(@PathVariable(name = "id") int id, @RequestBody Album album) {
        return ResponseEntity.ok(albumService.updateById(id, album));
    }
}