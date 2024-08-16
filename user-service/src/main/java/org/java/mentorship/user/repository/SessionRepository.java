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
    @Select("""
            <script>
            SELECT * FROM sessions
                <where> 
                <if test='userId != null'>
                AND user_id=#{userId}
                </if>
                </where>
            </script> 
            """)
    List<Session> find(Integer userId);

    void insert(Session session);

    @Select("SELECT * FROM sessions WHERE session_key=#{sessionKey}")
    Optional<Session> getByKey(String key);
}
