package org.java.mentorship.persistence;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.common.EntityRepository;
import org.java.mentorship.domain.Song;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;


@Repository
@RequiredArgsConstructor
public class SongRepository implements EntityRepository<Song, Integer> {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Song> rowMapper;

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    @Override
    public Song save(final Song song) {
        jdbcTemplate.update("INSERT INTO songs (name, style, duration) VALUES(?,?,?)",
                song.getName(), song.getStyle(), song.getDuration());

        return song;
    }
//    @Override
//    public Song create(final Song song) {
//        jdbcTemplate.update("INSERT INTO songs (name, style, duration,artistId,albumId) VALUES(?,?,?,?,?)",
//                song.getName(), song.getStyle(), song.getDuration(), song.getArtistId(), song.getAlbumId());
//
//        return song;
//    }
    @Override
    public Song findById(final Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM songs WHERE id = ?", rowMapper, id);
    }

    @Override
    public Song update(final Integer id, final Song song){
        findById(id);
        jdbcTemplate.update("UPDATE songs SET name = ?, style = ?, duration = ?, artist_id = ?, album_id = ? WHERE id = ?",
                song.getName(), song.getStyle(), song.getDuration(), song.getArtistId(), song.getAlbumId(), song.getId());
        return song;
    }

    @Override
    public boolean delete(final Integer id) {
        findById(id);
        jdbcTemplate.update("DELETE FROM songs WHERE id = ?", id);
        return true;
    }

    public List<Song> find(Map<String, Object> params) {
        String baseQuery = "SELECT * FROM songs";
        StringJoiner joiner = new StringJoiner(" AND "," WHERE ", "");
        params.forEach((key, value) ->
            joiner.add(key + " = ?" ));

        String finalQuery = baseQuery + (params.size()!=0 ? joiner.toString() : "");
        System.out.println(joiner + " " + joiner.length());
        Object[] paramValues = params.values().toArray();
        return jdbcTemplate.query(finalQuery, paramValues, rowMapper);
    }


    public List<Song> findSongs(Integer artistId) {
        artistRepository.findById(artistId);
        return jdbcTemplate.query("SELECT * FROM songs WHERE artist_id = ? ", rowMapper,artistId);
    }

    public List<Song> findSongsByAlbumId(Integer albumId) {
        albumRepository.findById(albumId);
        return jdbcTemplate.query("SELECT * FROM songs WHERE album_id = ? ", rowMapper, albumId);

    }
}

