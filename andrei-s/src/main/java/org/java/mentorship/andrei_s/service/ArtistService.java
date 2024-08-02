package org.java.mentorship.andrei_s.service;

import org.java.mentorship.andrei_s.common.EntityRepository;
import org.java.mentorship.andrei_s.common.EntityService;
import org.java.mentorship.andrei_s.domain.Artist;
import org.springframework.stereotype.Service;

@Service
public class ArtistService extends EntityService<Artist> {
    ArtistService(EntityRepository<Artist> repo) {
        super(repo, "artist");
    }
}
