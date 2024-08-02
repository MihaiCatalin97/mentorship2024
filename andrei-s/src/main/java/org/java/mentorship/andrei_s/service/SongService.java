package org.java.mentorship.andrei_s.service;

import org.java.mentorship.andrei_s.common.EntityRepository;
import org.java.mentorship.andrei_s.common.EntityService;
import org.java.mentorship.andrei_s.domain.Song;
import org.springframework.stereotype.Service;

@Service
public class SongService extends EntityService<Song> {
    SongService(EntityRepository<Song> repo) {
        super(repo, "song");
    }
}
