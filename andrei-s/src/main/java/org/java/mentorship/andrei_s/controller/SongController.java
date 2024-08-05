package org.java.mentorship.andrei_s.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.andrei_s.domain.Song;
import org.java.mentorship.andrei_s.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;

    @GetMapping()
    public ResponseEntity<List<Song>> getAll(@RequestParam(required = false, name = "style") String style,
                                             @RequestParam(required = false, name = "artist") Integer artistId,
                                             @RequestParam(required = false, name = "album") Integer albumId) {
        Map<String, Object> filtersMap = new HashMap<>();

        filtersMap.put("style", style);
        filtersMap.put("artist_id", artistId);
        filtersMap.put("album_id", albumId);

        return ResponseEntity.ok(songService.find(filtersMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getById(@PathVariable(name = "id") int id) {
        Song song = songService.getById(id);
        return ResponseEntity.ok(song);
    }

    @PostMapping()
    public ResponseEntity<Song> createNew(@RequestBody Song song) {
        Song newSong = songService.createNew(song);
        return ResponseEntity.ok(newSong);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable(name = "id") int id) {
        songService.deleteById(id);
        return ResponseEntity.ok("Deleted song");
    }

    @PutMapping("/{id}")
    ResponseEntity<Song> modify(@PathVariable(name = "id") int id, @RequestBody Song song) {
        return ResponseEntity.ok(songService.updateById(id, song));
    }

    @GetMapping("/hello")
    ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello!!!");
    }
}
