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

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistService artistService;

    @GetMapping()
    public ResponseEntity<List<Artist>> getAll()
    {
        return ResponseEntity.ok(artistService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getById(@PathVariable(name = "id") int id)
    {
        Artist artist = artistService.getById(id);
        return ResponseEntity.ok(artist);
    }

    @PostMapping()
    public ResponseEntity<Artist> createNewSong(@RequestBody Artist artist)
    {
        Artist newArtist = artistService.createNew(artist);
        return ResponseEntity.ok(newArtist);
    }

    @DeleteMapping("/{id}") ResponseEntity<String> deleteSong(@PathVariable(name = "id") int id)
    {
        artistService.deleteById(id);
        return ResponseEntity.ok("Deleted artist");
    }

    @PutMapping("/{id}") ResponseEntity<Artist> modifySong(@PathVariable(name = "id") int id, @RequestBody Artist artist)
    {
        return ResponseEntity.ok(artistService.updateById(id, artist));
    }


}
