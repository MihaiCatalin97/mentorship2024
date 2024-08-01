package org.java.mentorship.controller;

import lombok.AllArgsConstructor;
import org.java.mentorship.domain.Album;
import org.java.mentorship.domain.Song;
import org.java.mentorship.service.AlbumService;
import org.java.mentorship.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Controller
@AllArgsConstructor
public class AlbumController {
    private final AlbumService albumService;
    private final SongService songService;

    @GetMapping("/albums")
    public ResponseEntity<List<Album>> getAllalbums() {
        return ResponseEntity.ok(albumService.findAll());
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable int id) {
        return ResponseEntity.ok(albumService.findById(id));
    }

    @GetMapping("/albums/{id}/songs")
    public ResponseEntity<List<Song>> getSongsByAlbum(@PathVariable int id) {
        return ResponseEntity.ok(songService.findSongsByAlbumId(id));
    }

    @PostMapping("/albums")
    public ResponseEntity<Album> create(@RequestBody final Album album) {
        return ResponseEntity
                .status(CREATED)
                .body(albumService.save(album));
    }

    @PutMapping("/albums/{id}")
    public ResponseEntity<Album> update(@PathVariable("id") final int identifier,
                                         @RequestBody final Album album) {
        album.setId(identifier);

        return ResponseEntity.ok(albumService.update(identifier,album));
    }

    @DeleteMapping("/albums/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final int identifier) {
        List<Song> songs = songService.findSongsByAlbumId(identifier);
        if(!songs.isEmpty()) {
            return ResponseEntity.badRequest().body("We need to delete all songs before deleting the album");
        }
       albumService.delete(identifier);
        return ResponseEntity.ok("deleted album");
    }
}
