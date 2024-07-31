package org.java.mentorship.andrei_s.controller;

import org.java.mentorship.andrei_s.domain.Song;
import org.java.mentorship.andrei_s.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class SongController {
    private final SongService songService;

    SongController(SongService songService,
                   Map<String, String> properties)
    {
        this.songService = songService;
    }

    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getAllSongs(@RequestParam(required = false, name = "style") String style)
    {
        return ResponseEntity.ok(songService.findAll(style));
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable(name = "id") int id)
    {
        Song song = songService.getSongById(id);
        if (song != null) return ResponseEntity.ok(song);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/songs")
    public ResponseEntity<Song> createNewSong(@RequestBody Song song)
    {
        Song newSong = songService.createNew(song);
        if (newSong != null)
            return ResponseEntity.ok(newSong);
        else
            return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/songs/{id}") ResponseEntity<String> deleteSong(@PathVariable(name = "id") int id)
    {
        if (songService.deleteSong(id))
            return ResponseEntity.ok("Deleted song");
        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/songs/{id}") ResponseEntity<Song> modifySong(@PathVariable(name = "id") int id, @RequestBody Song song)
    {
        Song modifiedSong = songService.modifySong(id, song);
        if (modifiedSong != null)
            return ResponseEntity.ok(modifiedSong);
        else
            return ResponseEntity.notFound().build();
    }
}
