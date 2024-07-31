package org.java.mentorship.andrei_s.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.andrei_s.domain.Song;
import org.java.mentorship.andrei_s.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;

    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getAll(@RequestParam(required = false, name = "style") String style)
    {
        return ResponseEntity.ok(songService.findAll(style));
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getById(@PathVariable(name = "id") int id)
    {
        Song song = songService.getById(id);
        return ResponseEntity.ok(song);
    }

    @PostMapping("/songs")
    public ResponseEntity<Song> createNew(@RequestBody Song song)
    {
        Song newSong = songService.createNew(song);
        return ResponseEntity.ok(newSong);
    }

    @DeleteMapping("/songs/{id}") ResponseEntity<String> delete(@PathVariable(name = "id") int id)
    {
        songService.deleteById(id);
        return ResponseEntity.ok("Deleted song");
    }

    @PutMapping("/songs/{id}") ResponseEntity<Song> modify(@PathVariable(name = "id") int id, @RequestBody Song song)
    {
        return ResponseEntity.ok(songService.updateById(id, song));
    }
}
