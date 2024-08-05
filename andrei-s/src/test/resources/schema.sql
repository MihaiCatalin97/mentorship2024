CREATE TABLE artists
(
    id   INT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE albums
(
    id        INT GENERATED ALWAYS AS IDENTITY,
    name      VARCHAR(255) NOT NULL,
    artist_id INT          NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_album_artist
        FOREIGN KEY (artist_id)
            REFERENCES artists (id)
);

CREATE TABLE songs
(
    id        INT GENERATED ALWAYS AS IDENTITY,
    name      VARCHAR(255) NOT NULL,
    style     VARCHAR(255) NOT NULL,
    duration  INT          NOT NULL,
    artist_id INT          NOT NULL,
    album_id  INT          NOT NULL,
    PRIMARY KEY (id),

    CONSTRAINT fk_song_artist
        FOREIGN KEY (artist_id)
            REFERENCES artists (id),

    CONSTRAINT fk_song_album
        FOREIGN KEY (album_id)
            REFERENCES albums (id)
);

INSERT INTO artists (name)
VALUES ('Artist 1');
INSERT INTO artists (name)
VALUES ('Artist 2');
INSERT INTO artists (name)
VALUES ('Artist 3');

INSERT INTO albums (name, artist_id)
VALUES ('Album 1', 1);
INSERT INTO albums (name, artist_id)
VALUES ('Album 2', 2);
INSERT INTO albums (name, artist_id)
VALUES ('Album 3', 2);

INSERT INTO songs (name, duration, album_id, artist_id, style)
VALUES ('Song',
        160,
        1, 1, 'Style 1');
INSERT INTO songs (name, duration, album_id, artist_id, style)
VALUES ('Song 2',
        190,
        2, 2, 'Style 2');
INSERT INTO songs (name, duration, album_id, artist_id, style)
VALUES ('Song 3',
        190,
        1, 2, 'Style 2');