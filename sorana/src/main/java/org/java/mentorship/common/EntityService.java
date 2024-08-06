package org.java.mentorship.common;

import org.java.mentorship.domain.Song;

import java.util.List;
import java.util.Map;

public interface EntityService<T, I> {
    default T save(final T song) {
        throw new UnsupportedOperationException("Not implemented: save");
    }

    default List<T> find(Map<String, Object> params) {
        throw new UnsupportedOperationException("Not implemented: findBy");
    }

    default T findById(final I id) {
        throw new UnsupportedOperationException("Not implemented: findById");
    }

    default Song update(final Integer id, final Song song) {
        throw new UnsupportedOperationException("Not implemented: update");
    }

    default boolean delete(final Integer id) {
        throw new UnsupportedOperationException("Not implemented: delete");
    }

    default List<T> findSongs(Integer artistId) {
        throw new UnsupportedOperationException("Not implemented: findSong");
    }

    default List<T> findSongsByAlbumId(Integer albumId) {
        throw new UnsupportedOperationException("Not implemented: findSongByAlbumId");
    }
}
