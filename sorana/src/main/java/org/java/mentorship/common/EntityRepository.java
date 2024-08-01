package org.java.mentorship.common;

import org.java.mentorship.domain.Song;

import java.util.List;

public interface EntityRepository<T, I>{
    default T save(final T song) {
        throw new UnsupportedOperationException("Not implemented: findById");
    }

//    Song create(Song song);

    default List<T> find() {
        throw new UnsupportedOperationException("Not implemented: findById");
    }

    default T findById(final I id) {
        throw new UnsupportedOperationException("Not implemented: findById");
    }

    default Song update(final T song) {
        throw new UnsupportedOperationException("Not implemented: update");
    }

    Song update(Integer id, Song song);

    default boolean delete(final I id) {
        throw new UnsupportedOperationException("Not implemented: delete");
    }
}
