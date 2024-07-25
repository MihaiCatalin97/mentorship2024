package org.java.mentorship.service;

import org.java.mentorship.domain.Song;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    public Song getSong(){
        Song song = new Song();

        return song;
    }
}
