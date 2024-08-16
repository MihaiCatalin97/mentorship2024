package org.java.mentorship.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.java.mentorship.user.domain.SessionEntity;

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
    List<SessionEntity> find(Integer userId);

    void insert(SessionEntity session);

    @Select("SELECT * FROM sessions WHERE session_key=#{sessionKey}")
    Optional<SessionEntity> getByKey(String key);
}
