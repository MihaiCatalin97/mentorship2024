package org.java.mentorship.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.common.EntityService;
import org.java.mentorship.domain.Artist;
import org.java.mentorship.domain.Song;
import org.java.mentorship.persistence.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService implements EntityService<Artist, Integer> {

}
