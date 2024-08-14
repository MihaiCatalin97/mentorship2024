package org.java.mentorship.user.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.java.mentorship.contracts.user.dto.Session;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SessionRepository {
    @Select("SELECT * FROM sessions")
    List<Session> findAll();

    @Insert("INSERT INTO sessions(session_key, expires_at, user_id) VALUES (#{sessionKey}, #{expiresAt}, #{userId})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void insert(Session session);

    @Select("SELECT * FROM sessions WHERE session_key=#{sessionKey}")
    Optional<Session> getByKey(String key);

    @Select("SELECT * FROM sessions WHERE user_id=#{id}")
    List<Session> getByUser(Integer id);

    @Select("SELECT * FROM sessions WHERE user_id=#{id} AND expires_at > NOW()")
    List<Session> getActiveByUser(Integer id);
}
