package org.java.mentorship.andrei_s.common;

import org.java.mentorship.andrei_s.exception.AppException;
import org.java.mentorship.andrei_s.exception.EntityNotFound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class EntityService<T extends Entity> {
    private final String entityName;
    protected EntityRepository<T> repo;

    public EntityService(EntityRepository<T> repo, String entityName) {
        this.repo = repo;
        this.entityName = entityName;
    }

    public T getById(Integer id) {
        try {
            return this.repo.getById(id);
        } catch (NoSuchElementException e) {
            throw new EntityNotFound(id, entityName);
        }
    }

    public List<T> find() {
        return this.find(new HashMap<>());
    }

    public List<T> find(Map<String, Object> filters) {
        return repo.find(filters);
    }

    public T createNew(T entity) {
        entity.validate();
        return repo.createNew(entity);
    }

    public void deleteById(Integer id) {
        this.getById(id);
        try {
            repo.deleteById(id);
        } catch (Exception e) {
            throw new AppException("Unknown error when deleting " + entityName + ". Does it still contain songs?", e);
        }
    }

    public T updateById(Integer id, T modified) {
        repo.getById(id);
        modified.validate();
        return repo.updateById(id, modified);
    }

    public List<T> searchBy(String field, String query) {
        return repo.searchBy(field, query);
    }
}
