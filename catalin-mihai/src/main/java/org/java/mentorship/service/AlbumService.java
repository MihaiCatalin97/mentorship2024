package org.java.mentorship.service;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.common.EntityService;
import org.java.mentorship.domain.Album;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlbumService implements EntityService<Album, Integer> {

}
