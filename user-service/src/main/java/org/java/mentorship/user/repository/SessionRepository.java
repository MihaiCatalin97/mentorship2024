package org.java.mentorship.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.java.mentorship.user.domain.SessionEntity;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SessionRepository {
    List<SessionEntity> find(Integer userId);

    void insert(SessionEntity session);

    Optional<SessionEntity> getByKey(String key);

    void delete(Integer id);
}
