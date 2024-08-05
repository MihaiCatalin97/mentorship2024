-- Insert test data into the artists table
INSERT INTO artists (name) VALUES ('Artist 1');
INSERT INTO artists (name) VALUES ('Artist 2');

-- Insert test data into the albums table
INSERT INTO albums (name, artist_id) VALUES ('Album 1', 1);
INSERT INTO albums (name, artist_id) VALUES ('Album 2', 2);

-- Insert test data into the songs table
INSERT INTO songs (name, style, duration, artist_id, album_id) VALUES ('Test Song 1', 'Pop', 100, 1, 1);
INSERT INTO songs (name, style, duration, artist_id, album_id) VALUES ('Test Song 2', 'Rock', 180, 2, 2);
