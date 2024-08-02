CREATE TABLE artists(
	id INT GENERATED ALWAYS AS IDENTITY,
	name VARCHAR(255) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE albums(
	id INT GENERATED ALWAYS AS IDENTITY,
	name VARCHAR(255) NOT NULL,
	artist_id INT NOT NULL,
	PRIMARY KEY(id),
	CONSTRAINT fk_album_artist
		FOREIGN KEY(artist_id)
			REFERENCES artists(id)
);

CREATE TABLE songs(
	id INT GENERATED ALWAYS AS IDENTITY,
	name VARCHAR(255) NOT NULL,
	style VARCHAR(255) NOT NULL,
	duration INT NOT NULL,
	artist_id INT NOT NULL,
	album_id INT NOT NULL,
	PRIMARY KEY(id),
	
	CONSTRAINT fk_song_artist
		FOREIGN KEY(artist_id)
			REFERENCES artists(id),
	
	CONSTRAINT fk_song_album
		FOREIGN KEY(album_id)
			REFERENCES albums(id)
);