package org.java.mentorship.common;

import java.util.List;

public interface EntityRepository<T, I> {

    default T save(final T entity) {
        throw new UnsupportedOperationException("Not implemented: save");
    }

    default List<T> findAll() {
        throw new UnsupportedOperationException("Not implemented: findAll");
    }

    default T findById(final I id) {
        throw new UnsupportedOperationException("Not implemented: findById");
    }

    default T update(final T entity) {
        throw new UnsupportedOperationException("Not implemented: update");
    }

    default T delete(final I id) {
        throw new UnsupportedOperationException("Not implemented: delete");
    }
}
