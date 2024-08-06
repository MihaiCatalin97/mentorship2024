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
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping("/albums")
    public ResponseEntity<Album> create(@RequestBody final Album album) {
        return ResponseEntity
                .status(CREATED)
                .body(albumService.save(album));
    }
}