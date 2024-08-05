DROP TABLE IF EXISTS songs;
DROP TABLE IF EXISTS albums;
DROP TABLE IF EXISTS artists;

CREATE TABLE artists (
                         id INT GENERATED ALWAYS AS IDENTITY,
                         name VARCHAR(255) NOT NULL,
                         PRIMARY KEY(id)
);

CREATE TABLE albums (
                        id INT GENERATED ALWAYS AS IDENTITY,
                        name VARCHAR(255) NOT NULL,
                        artist_id INT,
                        PRIMARY KEY(id),
                        CONSTRAINT fk_album_artist
                            FOREIGN KEY(artist_id)
                                REFERENCES artists(id)
                                ON DELETE CASCADE
);

CREATE TABLE songs (
                       id INT GENERATED ALWAYS AS IDENTITY,
                       name VARCHAR(255) NOT NULL,
                       style VARCHAR(255) NOT NULL,
                       duration INT NOT NULL,
                       artist_id INT,
                       album_id INT,
                       PRIMARY KEY(id),
                       CONSTRAINT fk_song_artist
                           FOREIGN KEY(artist_id)
                               REFERENCES artists(id)
                               ON DELETE CASCADE,
                       CONSTRAINT fk_song_album
                           FOREIGN KEY(album_id)
                               REFERENCES albums(id)
                               ON DELETE CASCADE
);
