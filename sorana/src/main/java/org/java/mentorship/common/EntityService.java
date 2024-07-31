package org.java.mentorship.common;

import org.java.mentorship.domain.Song;

import java.util.List;

public interface EntityService<T,I>{
    default T save(final T song) {
        throw new UnsupportedOperationException("Not implemented: findById");
    }

    default List<T> findAll() {
        throw new UnsupportedOperationException("Not implemented: findById");
    }

    default T findById(final I id) {
        throw new UnsupportedOperationException("Not implemented: findById");
    }

    default Song update(final T id, final T song) {
        throw new UnsupportedOperationException("Not implemented: update");
    }

    Song update(Integer id, Song song);

    default boolean delete(final I id) {
        throw new UnsupportedOperationException("Not implemented: delete");
    }
}
