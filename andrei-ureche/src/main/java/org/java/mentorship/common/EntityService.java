package org.java.mentorship.common;

import java.util.List;

public interface EntityService<T, I> {

    default T save(final T song) {
        throw new UnsupportedOperationException("Not implemented: findById");
    }

    default List<T> findAll() {
        throw new UnsupportedOperationException("Not implemented: findById");
    }

    default T findById(final I id) {
        throw new UnsupportedOperationException("Not implemented: findById");
    }

    default T update(final T song) {
        throw new UnsupportedOperationException("Not implemented: update");
    }

    default T delete(final I id) {
        throw new UnsupportedOperationException("Not implemented: delete");
    }
}