package org.java.mentorship.andrei_s.service;

import org.java.mentorship.andrei_s.common.EntityRepository;
import org.java.mentorship.andrei_s.common.EntityService;
import org.java.mentorship.andrei_s.domain.Album;
import org.springframework.stereotype.Service;

@Service
public class AlbumService extends EntityService<Album> {

    AlbumService(EntityRepository<Album> repo) {
        super(repo, "album");
    }

}
