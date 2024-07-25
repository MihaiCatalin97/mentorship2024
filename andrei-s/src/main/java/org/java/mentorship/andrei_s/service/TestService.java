package org.java.mentorship.andrei_s.service;

import org.java.mentorship.andrei_s.domain.Song;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public Song getSong()
    {
        Song song = new Song();

        return song;
    }

}
