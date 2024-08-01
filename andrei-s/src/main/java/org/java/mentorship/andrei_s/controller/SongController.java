package org.java.mentorship.andrei_s.controller;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.andrei_s.domain.Album;
import org.java.mentorship.andrei_s.domain.Artist;
import org.java.mentorship.andrei_s.domain.Song;
import org.java.mentorship.andrei_s.service.AlbumService;
import org.java.mentorship.andrei_s.service.ArtistService;
import org.java.mentorship.andrei_s.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;

    @GetMapping()
    public ResponseEntity<List<Song>> getAll(@RequestParam(required = false, name = "style") String style,
                                             @RequestParam(required = false, name = "artist") Integer artistId,
                                             @RequestParam(required = false, name = "album") Integer albumId)
    {
        return ResponseEntity.ok(songService.find(style, artistId, albumId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getById(@PathVariable(name = "id") int id)
    {
        Song song = songService.getById(id);
        return ResponseEntity.ok(song);
    }

    @PostMapping()
    public ResponseEntity<Song> createNew(@RequestBody Song song)
    {
        Song newSong = songService.createNew(song);
        return ResponseEntity.ok(newSong);
    }

    @DeleteMapping("/{id}") ResponseEntity<String> delete(@PathVariable(name = "id") int id)
    {
        songService.deleteById(id);
        return ResponseEntity.ok("Deleted song");
    }

    @PutMapping("/{id}") ResponseEntity<Song> modify(@PathVariable(name = "id") int id, @RequestBody Song song)
    {
        return ResponseEntity.ok(songService.updateById(id, song));
    }
}
