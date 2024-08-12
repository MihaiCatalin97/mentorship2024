package org.java.mentorship.user.repository.mapper;

import org.apache.ibatis.annotations.*;
import org.java.mentorship.contracts.user.dto.Session;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SessionMapper {
    @Select("SELECT * FROM sessions")
    List<Session> findAll();

    @Insert("INSERT INTO sessions(session_key, expires_at, user_id) VALUES (#{sessionKey}, #{expiresAt}, #{userId})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void insertSession(Session session);

    @Select("SELECT * FROM sessions WHERE session_key=#{sessionKey}")
    Optional<Session> getSessionByKey(String key);

    @Select("SELECT * FROM sessions WHERE user_id=#{id}")
    List<Session> getSessionsByUser(Integer id);

    @Select("SELECT * FROM sessions WHERE user_id=#{id} AND expires_at > NOW()")
    List<Session> getActiveSessionsByUser(Integer id);
}
