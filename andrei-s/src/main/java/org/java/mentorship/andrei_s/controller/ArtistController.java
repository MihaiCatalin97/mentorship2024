package org.java.mentorship.andrei_s.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.java.mentorship.andrei_s.domain.Artist;
import org.java.mentorship.andrei_s.service.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistService artistService;

    @GetMapping()
    public ResponseEntity<List<Artist>> getAll() {
        return ResponseEntity.ok(artistService.find());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getById(@PathVariable(name = "id") int id) {
        Artist artist = artistService.getById(id);
        return ResponseEntity.ok(artist);
    }

    @PostMapping()
    public ResponseEntity<Artist> createNewArtist(HttpServletRequest request, @RequestBody Artist artist) {
        if (!request.isUserInRole("ADMIN")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Artist newArtist = artistService.createNew(artist);
        return ResponseEntity.ok(newArtist);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteSong(HttpServletRequest request, @PathVariable(name = "id") int id) {
        if (!request.isUserInRole("ADMIN")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        artistService.deleteById(id);
        return ResponseEntity.ok("Deleted artist");
    }

    @PutMapping("/{id}")
    ResponseEntity<Artist> modifySong(HttpServletRequest request, @PathVariable(name = "id") int id, @RequestBody Artist artist) {
        if (!request.isUserInRole("ADMIN")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(artistService.updateById(id, artist));
    }

    @GetMapping("/search")
    ResponseEntity<List<Artist>> searchArtist(@RequestParam(name = "query") String query) {
        return ResponseEntity.ok(artistService.searchBy("name", query));
    }


}
